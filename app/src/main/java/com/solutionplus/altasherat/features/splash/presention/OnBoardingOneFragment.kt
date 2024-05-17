package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingOneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingOneFragment : BaseFragment<FragmentOnBoardingOneBinding>() {

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        logger.debug("one")
        binding.cardView.welcomeText1.text=getString(R.string.onboarding_1_welcome)
        binding.cardView.welcomeText2.text=getString(R.string.onboarding_1_welcome_2)
        binding.cardView.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingOneFragment_to_onBoardingTwoFragment)

        }

    }
    companion object {
        private val logger = getClassLogger()
    }

}