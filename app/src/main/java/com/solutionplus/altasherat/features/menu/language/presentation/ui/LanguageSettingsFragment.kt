package com.solutionplus.altasherat.features.menu.language.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.android.extentions.showShortToast
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageSettingsBinding
import com.solutionplus.altasherat.features.menu.language.presentation.viewmodels.LanguageSettingsContract
import com.solutionplus.altasherat.features.menu.language.presentation.viewmodels.LanguageSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageSettingsFragment : BaseFragment<FragmentLanguageSettingsBinding>() {
    private val viewModel: LanguageSettingsViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        getLocal()
    }


    override fun onLoading(isLoading: Boolean) {}

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }
  private fun handleSingleEvent(event: LanguageSettingsContract.LanguageSettingsContractEvent){
      when(event){
          is LanguageSettingsContract.LanguageSettingsContractEvent.ShowWorkerStateToast ->{
              requireContext().showShortToast (event.workerState)
          }

          is LanguageSettingsContract.LanguageSettingsContractEvent.SaveNavigation -> {
                  updateLocale(event.language)
                  findNavController().popBackStack()

          }

       is  LanguageSettingsContract.LanguageSettingsContractEvent.BackNavigation -> {
              findNavController().popBackStack()
          }
      }

  }

    override fun viewInit() {
        binding.radioButton2.setOnClickListener {
            updateRadioButtons("en")
            viewModel.onActionTrigger(LanguageSettingsContract.LanguageSettingsContractAction.RadioButtonClick("en")) }

        binding.radioButton1.setOnClickListener {
            updateRadioButtons("ar")
            viewModel.onActionTrigger(LanguageSettingsContract.LanguageSettingsContractAction.RadioButtonClick("ar"))

        }
        binding.save.setOnClickListener {
            viewModel.onActionTrigger(LanguageSettingsContract.LanguageSettingsContractAction.SaveClick)
        }
        binding.buttonBack.setOnClickListener {
            viewModel.onActionTrigger(LanguageSettingsContract.LanguageSettingsContractAction.BackClick)

        }
    }
    private fun updateLocale(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
    private fun getLocal() {
        val language = AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()
        logger.debug("languages $language")
        if (language == "ar") {
            updateRadioButtons("ar")
        } else {
            updateRadioButtons("en")
        }
    }
    private fun updateRadioButtons(selectedLanguage: String) {
        if (selectedLanguage == "ar") {
            binding.radioButton1.isEnabled = false
            binding.radioButton1.isChecked = true
            binding.radioButton2.isEnabled = true
            binding.radioButton2.isChecked = false
        } else {
            binding.radioButton1.isEnabled = true
            binding.radioButton1.isChecked = false
            binding.radioButton2.isEnabled = false
            binding.radioButton2.isChecked = true
        }
    }
    companion object {
        private val logger = getClassLogger()
    }

}