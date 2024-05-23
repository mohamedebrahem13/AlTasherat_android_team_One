package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageBinding
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {

    private val viewModel: LocalCountriesViewModel by viewModels()
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger( CountryLocalContract.CountryLocalAction.FetchCountriesFromLocal)
    }

    override fun onLoading(isLoading: Boolean) {}

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }

    override fun viewInit() {
        binding.buttonContinue.setOnClickListener {
            val userPreference = getUserPreference()
            viewModel.onActionTrigger(CountryLocalContract.CountryLocalAction.NextButtonClick(userPreference))
        }

         binding.radioButton2.setOnClickListener {
             viewModel.onActionTrigger( CountryLocalContract.CountryLocalAction.StartCountriesWorkerEn("en"))
        }
        binding.radioButton1.setOnClickListener {
            viewModel.onActionTrigger(CountryLocalContract.CountryLocalAction.StartCountriesWorkerAr("ar"))
        }


    }
    private fun handleSingleEvent(event: CountryLocalContract.CountryLocalEvent) {
        // Handle single events
        when(event){
            is CountryLocalContract.CountryLocalEvent.UpdateTheCountry->{
                val spinnerAdapter = CustomSpinnerAdapter(requireContext(),event.countries)
                binding.spinner.adapter = spinnerAdapter
            }
            is CountryLocalContract.CountryLocalEvent.NavigateToOnBoarding->{

                findNavController().navigate(R.id.action_languageFragment_to_viewPagerFragment)
            }
            is CountryLocalContract.CountryLocalEvent.StartCountriesWorker->{
                logger.debug("updateLocaleToEnglish")
                    updateLocale(event.language)
            }

            is CountryLocalContract.CountryLocalEvent.ShowWorkerStateToast ->showToast(event.workerState)
        }
    }
    private fun updateLocale(languageCode: String) {
        // Set the application locale using AppCompatDelegate
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }
    private fun getUserPreference():UserPreference {
        val preferredCountry = binding.spinner.selectedItem.toString()
      val language=  AppCompatDelegate.getApplicationLocales()
        val crunnetlangage =language[0]?.toLanguageTag()
        // Create a UserPreference object with the retrieved values
        val userPreference = UserPreference(preferredCountry, crunnetlangage.toString())
        return userPreference
        }
    companion object {
        private val logger = getClassLogger()
    }
}