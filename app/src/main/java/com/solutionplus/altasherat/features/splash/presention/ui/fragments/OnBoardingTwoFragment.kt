package com.solutionplus.altasherat.features.splash.presention.ui.fragments

import android.os.Bundle
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
        binding.card.textWelcome1.text = getString(R.string.onboarding_1_welcome)
        binding.card.textWelcome2.text = getString(R.string.onboarding_2_welcome_2)
    }

    companion object {
        private val logger = getClassLogger()
    }
}