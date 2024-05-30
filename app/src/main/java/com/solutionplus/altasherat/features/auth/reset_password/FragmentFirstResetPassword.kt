package com.solutionplus.altasherat.features.auth.reset_password

import android.os.Bundle
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentFirstResetPasswordBinding

class FragmentFirstResetPassword: BaseFragment<FragmentFirstResetPasswordBinding>() {

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


    companion object {
        val logger = getClassLogger()
    }
}