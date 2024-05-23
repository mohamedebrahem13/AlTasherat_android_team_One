package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingTwoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingTwoFragment : BaseFragment<FragmentOnBoardingTwoBinding>() {

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {
        logger.debug("Two")
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager)
        binding.card.textWelcome1.text = getString(R.string.onboarding_1_welcome)
        binding.card.textWelcome2.text = getString(R.string.onboarding_2_welcome_2)
        binding.card.buttonNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
        binding.buttonPrevious.setOnClickListener {
            viewPager?.currentItem = 0
        }
    }

    companion object {
        private val logger = getClassLogger()
    }
}