package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.showShortToast
import com.solutionplus.altasherat.android.extentions.showSnackBar
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountBinding
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountAction
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountEvent
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAccountFragment : BaseFragment<FragmentDeleteAccountBinding>() {

    private val viewModel: DeleteAccountViewModel by viewModels()

    override fun viewInit() {
        setFragmentResultListener(DeleteAccountDialogFragment.REQUEST_KEY) { _, bundle ->
            bundle.getString(DeleteAccountDialogFragment.PASSWORD_KEY)?.let {
                viewModel.processIntent(DeleteAccountAction.DeleteAccount(it))
            }
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        with(binding) {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            buttonConfirm.setOnClickListener {
                val action =
                    DeleteAccountFragmentDirections.actionDeleteAccountFragmentToDeleteAccountDialogFragment(
                        null
                    )
                findNavController().navigate(action)
            }

            buttonCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            onLoading(state.isLoading)

            state.exception?.let { exception ->
                handleException(
                    exception = exception,
                    action = state.action,
                    handleValidationErrors = ::handleValidationErrors
                )
            }
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is DeleteAccountEvent.AccountDeleted -> {
                    requireContext().showShortToast(event.message)
                    findNavController().popBackStack(R.id.fragment_visa_platform, false)
                }
            }
        }
    }

    private fun handleValidationErrors(exceptions: Map<String, String>) {
        val error = exceptions.values.first()
        findNavController().navigate(
            DeleteAccountFragmentDirections.actionDeleteAccountFragmentToDeleteAccountDialogFragment(
                error
            )
        )
    }

    override fun onLoading(isLoading: Boolean) {}

    override fun onRetryAction(action: ViewAction?, message: String) {
        showSnackBar(message) {
            action?.let { viewModel.processIntent(it as DeleteAccountAction) }
        }
    }
}