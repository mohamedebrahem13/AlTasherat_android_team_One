package com.solutionplus.altasherat.features.menu.verification.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentVerificationBinding

class VerificationFragment : BaseFragment<FragmentVerificationBinding>() {

    private val args: VerificationFragmentArgs by navArgs()

    override fun viewInit() {}

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val email = args.email

        binding.textVerificationDescription.text =
            getString(R.string.verification_description, email)

        binding.buttonSkip.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onLoading(isLoading: Boolean) {}

    override fun subscribeToObservables() {}

    override fun onRetryAction(action: ViewAction?, message: String) {}
}