package com.solutionplus.altasherat.features.menu.about.presentation

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.AboutLayoutBinding

class FragmentAbout: BaseFragment<AboutLayoutBinding>() {

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
}