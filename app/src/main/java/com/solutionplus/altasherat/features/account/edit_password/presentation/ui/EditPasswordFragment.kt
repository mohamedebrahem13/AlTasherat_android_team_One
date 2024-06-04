package com.solutionplus.altasherat.features.account.edit_password.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD_CONFIRMATION
import com.solutionplus.altasherat.common.domain.constants.Constants.OLD_PASSWORD
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
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
            if (state.exception is AlTasheratException.Local.RequestValidation) {
                handleValidationErrors(state.exception.errors)
            } else if (state.exception is AlTasheratException.Client.ResponseValidation) {
                handleValidationErrors(state.exception.errors)
            } else {
                handleValidationErrors(emptyMap())
            }
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is EditPasswordEvent.PasswordUpdated -> {
                    Toast.makeText(
                        requireContext(),
                        "Password updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
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

        if (errors.isNotEmpty()) {
            errors.forEach { (key, value) ->
                errorFields[key]?.error = value
            }
        } else {
            errorFields.values.forEach { it.error = null }
        }
    }
}