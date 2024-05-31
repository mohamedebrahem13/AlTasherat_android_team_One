package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseSheetFragment
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountDialogBinding

class DeleteAccountDialogFragment : BaseSheetFragment<FragmentDeleteAccountDialogBinding>() {

    override fun viewInit() {}

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.buttonConfirm.setOnClickListener {
            val password = binding.inputPassword.editText?.text.toString()
            setFragmentResult(REQUEST_KEY, Bundle().apply {
                putString(PASSWORD_KEY, password)
            })
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val REQUEST_KEY = "password_request"
        const val PASSWORD_KEY = "password"
    }
}