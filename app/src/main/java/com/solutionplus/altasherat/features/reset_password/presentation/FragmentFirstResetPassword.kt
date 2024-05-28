package com.solutionplus.altasherat.features.reset_password.presentation

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

    override fun onStart() {
        super.onStart()
        logger.debug("start")
    }

    override fun onPause() {
        super.onPause()
        logger.debug("pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.debug("destroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("create")

    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    companion object {
        val logger = getClassLogger()
    }
}