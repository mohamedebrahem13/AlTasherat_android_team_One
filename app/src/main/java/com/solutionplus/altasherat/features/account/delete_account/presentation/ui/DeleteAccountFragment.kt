package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountBinding

class DeleteAccountFragment : BaseFragment<FragmentDeleteAccountBinding>() {
    override fun viewInit() {
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
    }

    override fun onLoading(isLoading: Boolean) {
    }
}