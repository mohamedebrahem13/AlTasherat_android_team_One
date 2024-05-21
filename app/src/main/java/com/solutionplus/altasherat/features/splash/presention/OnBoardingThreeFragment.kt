package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingThreeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingThreeFragment : BaseFragment<FragmentOnBoardingThreeBinding>() {


    override fun onFragmentReady(savedInstanceState: Bundle?) {

    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        logger.debug("Three")

    }

    override fun viewInit() {
        logger.debug("Three")
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager)
        binding.cardView.welcomeText1.text=getString(R.string.onboarding_1_welcome)
        binding.cardView.welcomeText2.text=getString(R.string.onboarding_3_welcome_2)
        binding.imageView3.setOnClickListener {
            viewPager?.currentItem=1
        }
        binding.cardView.nextButton.setOnClickListener{
            viewPager?.currentItem=2

        }
    }
    companion object {
        private val logger = getClassLogger()
    }
}