package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingOneBinding


class OnBoardingOneFragment : BaseFragment<FragmentOnBoardingOneBinding>() {

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        logger.debug("one")
    }
    companion object {
        private val logger = getClassLogger()
    }

}