package com.solutionplus.altasherat.features.home.visa_request.presentation.ui

import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentVisaRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisaRequestFragment : BaseFragment<FragmentVisaRequestBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }

    override fun onRetryAction(action: ViewAction?, message: String) {}

}