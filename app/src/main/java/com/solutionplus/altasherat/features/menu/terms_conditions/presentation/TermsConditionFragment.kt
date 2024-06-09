package com.solutionplus.altasherat.features.menu.terms_conditions.presentation

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentTermsConditionsBinding

class TermsConditionFragment : BaseFragment<FragmentTermsConditionsBinding>() {

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }

    override fun onRetryAction(action: ViewAction?, message: String) {}
}