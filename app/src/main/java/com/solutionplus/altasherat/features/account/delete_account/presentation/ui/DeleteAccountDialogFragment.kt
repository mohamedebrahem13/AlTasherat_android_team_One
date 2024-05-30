package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseSheetFragment
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountDialogBinding
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountAction
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountEvent
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAccountDialogFragment : BaseSheetFragment<FragmentDeleteAccountDialogBinding>() {

    private val viewModel: DeleteAccountViewModel by viewModels()

    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.buttonConfirm.setOnClickListener {
            val password = binding.inputPassword.editText?.text.toString()
            viewModel.processIntent(DeleteAccountAction.DeleteAccount(password))
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) {}

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is DeleteAccountEvent.AccountDeleted -> {
                    requireActivity().finish()
                    startActivity(requireActivity().intent)
                }
            }
        }
    }
}