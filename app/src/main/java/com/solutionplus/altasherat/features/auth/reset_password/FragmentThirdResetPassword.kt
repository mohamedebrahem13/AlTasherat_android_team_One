package com.solutionplus.altasherat.features.auth.reset_password

import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentThirdResetPasswordBinding

class FragmentThirdResetPassword: BaseFragment<FragmentThirdResetPasswordBinding>() {

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onRetryAction(action: ViewAction?, message: String) {}
}