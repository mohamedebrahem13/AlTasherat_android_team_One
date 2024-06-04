package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
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
                    DeleteAccountFragmentDirections.actionDeleteAccountFragmentToDeleteAccountDialogFragment()
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
            if (state.exception is AlTasheratException.Local.RequestValidation) {
                val error = getString(state.exception.errors.values.first())
                Toast.makeText(
                    requireContext(),
                    error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is DeleteAccountEvent.AccountDeleted -> {
                    requireActivity().finish()
                    startActivity(requireActivity().intent)
                }
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {}
}