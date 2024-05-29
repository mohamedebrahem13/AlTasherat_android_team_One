package com.solutionplus.altasherat.features.home.language

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguage2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguage2Binding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
       binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }


}