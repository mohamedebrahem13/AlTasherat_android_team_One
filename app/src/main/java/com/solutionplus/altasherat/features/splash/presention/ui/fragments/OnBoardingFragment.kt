package com.solutionplus.altasherat.features.splash.presention.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentViewPagerBinding
import com.solutionplus.altasherat.features.home.presentation.HomeActivity
import com.solutionplus.altasherat.features.splash.presention.ui.adapter.ViewPagerAdapter
import com.solutionplus.altasherat.features.splash.presention.viewmodels.OnBoardingThreeContract
import com.solutionplus.altasherat.features.splash.presention.viewmodels.OnBoardingThreeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentViewPagerBinding>() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: ViewPagerAdapter
    private val viewModel: OnBoardingThreeViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Show the button in fragment 2 and 3, hide it in fragment 1
                if (position == 0) {
                    binding.buttonPrevious.visibility = View.GONE
                    binding.textPrevious.visibility = View.GONE
                } else {
                    binding.buttonPrevious.visibility = View.VISIBLE
                    binding.textPrevious.visibility = View.VISIBLE
                }
            }
        })

        // Set click listener for the button
        binding.buttonPrevious.setOnClickListener {
            // Get the current item position
            val currentPosition = viewPager.currentItem
            // If it's not the first fragment, move to the previous fragment
            if (currentPosition > 0) {
                viewPager.currentItem = currentPosition - 1
            }
        }

        // Set click listener for the next button
        binding.buttonNext.setOnClickListener {
            // Get the current item position
            val currentPosition = viewPager.currentItem
            // If it's not the last fragment
            if (currentPosition < adapter.itemCount - 1) {
                viewPager.currentItem = currentPosition + 1
            } else if (currentPosition == adapter.itemCount - 1) {
                logger.debug("onboardingThree action")
                viewModel.onActionTrigger( OnBoardingThreeContract.OnBoardingThreeAction.SaveOnboardingShown)
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }
    private fun handleSingleEvent(event: OnBoardingThreeContract.OnBoardingThreeEvent) {
        //Last fragment, you can navigate to HomeActivity if needed
        when(event){
            is OnBoardingThreeContract.OnBoardingThreeEvent.NavigateToHome->{
                Intent(requireActivity(), HomeActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }


    }


        override fun viewInit() {
        viewPager = binding.viewpager
        tabLayout = binding.tabLayout
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            OnBoardingOneFragment(),
            OnBoardingTwoFragment(),
            OnBoardingThreeFragment()
        )
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, fragments)
        viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        }.attach()
    }


    companion object {
        private val logger = getClassLogger()
    }
}