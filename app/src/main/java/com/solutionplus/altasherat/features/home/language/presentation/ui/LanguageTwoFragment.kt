package com.solutionplus.altasherat.features.home.language.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguage2Binding
import com.solutionplus.altasherat.features.home.language.presentation.viewmodels.LanguageTwoContract
import com.solutionplus.altasherat.features.home.language.presentation.viewmodels.LanguageTwoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageTwoFragment : BaseFragment<FragmentLanguage2Binding>() {
    private val viewModel: LanguageTwoViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        getLocal()
    }


    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }
  private fun handleSingleEvent(event: LanguageTwoContract.LanguageTwoContractEvent){
      when(event){
          is LanguageTwoContract.LanguageTwoContractEvent.ShowWorkerStateToast ->showToast(event.workerState)
          is LanguageTwoContract.LanguageTwoContractEvent.SaveNavigation -> findNavController().popBackStack()

          is LanguageTwoContract.LanguageTwoContractEvent.StartCountriesWorker -> {
              updateLocale(event.language)

          }

          LanguageTwoContract.LanguageTwoContractEvent.BackNavigation ->findNavController().popBackStack()

      }

  }

    override fun viewInit() {
        binding.radioButton2.setOnClickListener {
            viewModel.onActionTrigger(LanguageTwoContract.LanguageTwoContractAction.StartCountriesWorkerEn("en"))

        }

        binding.radioButton1.setOnClickListener {
            viewModel.onActionTrigger(LanguageTwoContract.LanguageTwoContractAction.StartCountriesWorkerAr("ar"))

        }
        binding.save.setOnClickListener {
            viewModel.onActionTrigger(LanguageTwoContract.LanguageTwoContractAction.SaveClick)
        }
        binding.buttonBack.setOnClickListener {
            viewModel.onActionTrigger(LanguageTwoContract.LanguageTwoContractAction.BackClick)

        }
    }
    private fun updateLocale(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
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
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }
}