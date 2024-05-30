package com.solutionplus.altasherat.features.home.language.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguage2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguage2Binding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        getLocal()
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
    private fun getLocal() {
        val language = AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()
        logger.debug("langauges $language")
        if (language == "ar") {
            binding.radioButton1.isEnabled = false
            binding.radioButton2.isEnabled = true
            binding.radioButton1.isChecked = true
        } else{
            binding.radioButton2.isEnabled = false
            binding.radioButton1.isEnabled = true
            binding.radioButton2.isChecked = true
        }
    }
    companion object {
        private val logger = getClassLogger()
    }
}