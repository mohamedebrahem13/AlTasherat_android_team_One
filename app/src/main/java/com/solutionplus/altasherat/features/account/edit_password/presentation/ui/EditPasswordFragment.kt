package com.solutionplus.altasherat.features.account.edit_password.presentation.ui

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.android.extentions.showShortToast
import com.solutionplus.altasherat.android.extentions.showSnackBar
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD_CONFIRMATION
import com.solutionplus.altasherat.common.domain.constants.Constants.OLD_PASSWORD
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentEditPasswordBinding
import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordAction
import com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordEvent
import com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel.EditPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPasswordFragment : BaseFragment<FragmentEditPasswordBinding>() {

    private val viewModel: EditPasswordViewModel by viewModels()

    override fun viewInit() {}

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            val request = EditPasswordRequest(
                oldPassword = binding.inputOldPassword.editText?.text.toString(),
                newPassword = binding.inputNewPassword.editText?.text.toString(),
                confirmPassword = binding.inputNewPasswordConfirm.editText?.text.toString()
            )
            viewModel.processIntent(EditPasswordAction.EditPassword(request))
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
                is EditPasswordEvent.PasswordUpdated -> {
                    requireContext().showShortToast(event.message)
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {}

    private fun handleValidationErrors(errors: Map<String, String>) {
        val errorFields = mapOf(
            OLD_PASSWORD to binding.inputOldPassword,
            NEW_PASSWORD to binding.inputNewPassword,
            NEW_PASSWORD_CONFIRMATION to binding.inputNewPasswordConfirm,
        )

        errorFields.forEach { (key, field) ->
            field.error = errors[key]
            field.editText?.doAfterTextChanged { field.error = null }
        }
    }

    override fun onRetryAction(action: ViewAction?, message: String) {
        showSnackBar(message) {
            action?.let { viewModel.processIntent(it as EditPasswordAction) }
        }
    }
}