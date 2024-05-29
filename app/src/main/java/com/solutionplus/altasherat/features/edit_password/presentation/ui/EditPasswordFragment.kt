package com.solutionplus.altasherat.features.edit_password.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentEditPasswordBinding
import com.solutionplus.altasherat.features.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordAction
import com.solutionplus.altasherat.features.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordEvent
import com.solutionplus.altasherat.features.edit_password.presentation.viewmodel.EditPasswordViewModel

class EditPasswordFragment : BaseFragment<FragmentEditPasswordBinding>() {

    private val viewModel: EditPasswordViewModel by viewModels()

    override fun viewInit() {
    }


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
        collectFlowWithLifecycle(viewModel.viewState) {}

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is EditPasswordEvent.PasswordUpdated -> {
                    Toast.makeText(
                        requireContext(),
                        "Personal info updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }
}