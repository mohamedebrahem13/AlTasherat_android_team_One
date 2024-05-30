package com.solutionplus.altasherat.features.account.delete_account.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentAccountSettingBinding

class AccountSettingFragment : BaseFragment<FragmentAccountSettingBinding>() {
    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        with(binding) {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            textDeactivateAccount.setOnClickListener {
                val action =
                    AccountSettingFragmentDirections.actionAccountSettingFragmentToDeleteAccountFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun subscribeToObservables() {
    }

    override fun onLoading(isLoading: Boolean) {
    }
}