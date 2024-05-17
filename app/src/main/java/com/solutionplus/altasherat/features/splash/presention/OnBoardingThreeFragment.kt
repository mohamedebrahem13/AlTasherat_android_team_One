package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.navigation.fragment.findNavController
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
        binding.cardView.indicator.indicator2.setBackgroundResource(R.drawable.indicator_shape_2)
        binding.cardView.welcomeText1.text=getString(R.string.onboarding_1_welcome)
        binding.cardView.welcomeText2.text=getString(R.string.onboarding_3_welcome_2)
        binding.customToolbar.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    companion object {
        private val logger = getClassLogger()
    }
}