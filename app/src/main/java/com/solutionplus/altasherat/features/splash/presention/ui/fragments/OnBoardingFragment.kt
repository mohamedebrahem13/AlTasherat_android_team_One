package com.solutionplus.altasherat.features.splash.presention.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnboardingBinding
import com.solutionplus.altasherat.features.auth.ui.AuthActivity
import com.solutionplus.altasherat.features.splash.presention.ui.adapter.OnboardingPageAdapter
import com.solutionplus.altasherat.features.splash.presention.viewmodels.OnBoardingThreeContract
import com.solutionplus.altasherat.features.splash.presention.viewmodels.OnBoardingThreeViewModel
import dagger.hilt.android.AndroidEntryPoint
data class OnboardingPage(val imageResId: Int, val description: Int)

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: OnboardingPageAdapter
    private val viewModel: OnBoardingThreeViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.buttonPrevious.visibility = View.GONE
                    binding.textPrevious.visibility = View.GONE
                } else {
                    binding.buttonPrevious.visibility = View.VISIBLE
                    binding.textPrevious.visibility = View.VISIBLE
                }
            }
        })

        binding.buttonPrevious.setOnClickListener {
            val currentPosition = viewPager.currentItem
            if (currentPosition > 0) {
                viewPager.currentItem = currentPosition - 1
            }
        }

        binding.buttonNext.setOnClickListener {
            val currentPosition = viewPager.currentItem
            if (currentPosition < adapter.itemCount - 1) {
                viewPager.currentItem = currentPosition + 1
            } else if (currentPosition == adapter.itemCount - 1) {
                logger.debug("onboardingThree action")
                viewModel.onActionTrigger(OnBoardingThreeContract.OnBoardingThreeAction.SaveOnboardingShown)
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
        when (event) {
            is OnBoardingThreeContract.OnBoardingThreeEvent.NavigateToHome -> {
                Intent(requireActivity(), AuthActivity::class.java).also { intent ->
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
        val pages = listOf(
            OnboardingPage(R.drawable.image_bag, R.string.onboarding_1_welcome_2),
            OnboardingPage(R.drawable.image_stamp, R.string.onboarding_2_welcome_2) ,
            OnboardingPage(R.drawable.image_passport,R.string.onboarding_1_welcome_2)

        // Add more pages as needed
        )
        adapter = OnboardingPageAdapter(requireContext(),pages)
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