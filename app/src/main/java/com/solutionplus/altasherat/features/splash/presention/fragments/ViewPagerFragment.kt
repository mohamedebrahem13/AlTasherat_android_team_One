package com.solutionplus.altasherat.features.splash.presention.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.databinding.FragmentViewPagerBinding
import com.solutionplus.altasherat.features.splash.presention.adapter.ViewPagerAdapter
import com.solutionplus.altasherat.features.splash.presention.fragments.OnBoardingOneFragment
import com.solutionplus.altasherat.features.splash.presention.fragments.OnBoardingThreeFragment
import com.solutionplus.altasherat.features.splash.presention.fragments.OnBoardingTwoFragment


class ViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: ViewPagerAdapter
    private var _binding: FragmentViewPagerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewpager
        tabLayout = binding.tabLayout
        setupViewPager()
        setupTabLayout()

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
                // Last fragment, you can navigate to another activity if needed
                // Intent(requireActivity(), AnotherActivity::class.java).also { intent ->
                //     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                //     startActivity(intent)
                // }
            }
        }
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}