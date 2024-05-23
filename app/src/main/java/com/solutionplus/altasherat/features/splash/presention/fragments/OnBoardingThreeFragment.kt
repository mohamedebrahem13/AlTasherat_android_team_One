package com.solutionplus.altasherat.features.splash.presention.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingThreeBinding
import com.solutionplus.altasherat.features.splash.presention.contracts.OnBoardingThreeContract
import com.solutionplus.altasherat.features.splash.presention.viewmodels.OnBoardingThreeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingThreeFragment : BaseFragment<FragmentOnBoardingThreeBinding>() {

    private val viewModel: OnBoardingThreeViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger( OnBoardingThreeContract.OnBoardingThreeAction.SaveOnboardingShown)

    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        logger.debug("Three")

    }

    override fun viewInit() {
        logger.debug("Three")
        binding.card.textWelcome1.text = getString(R.string.onboarding_1_welcome)
        binding.card.textWelcome2.text = getString(R.string.onboarding_3_welcome_2)
    }

    companion object {
        private val logger = getClassLogger()
    }
}