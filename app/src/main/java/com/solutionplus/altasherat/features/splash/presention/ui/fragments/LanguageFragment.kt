package com.solutionplus.altasherat.features.splash.presention.ui.fragments

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
import com.solutionplus.altasherat.features.splash.presention.ui.adapter.CustomSpinnerAdapter
import com.solutionplus.altasherat.features.splash.presention.viewmodels.LanguageContract
import com.solutionplus.altasherat.features.splash.presention.viewmodels.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {

    private val viewModel: LanguageViewModel by viewModels()
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger( LanguageContract.CountryLocalAction.FetchCountriesFromLocal)
    }

    override fun onLoading(isLoading: Boolean) {}

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }

    override fun viewInit() {
        binding.buttonContinue.setOnClickListener {
            val preferredCountry = binding.spinner.selectedItem.toString()
            viewModel.onActionTrigger(LanguageContract.CountryLocalAction.NextButtonClick(preferredCountry))
        }

         binding.radioButton2.setOnClickListener {
             viewModel.onActionTrigger( LanguageContract.CountryLocalAction.StartCountriesWorkerEn("en"))
        }
        binding.radioButton1.setOnClickListener {
            viewModel.onActionTrigger(LanguageContract.CountryLocalAction.StartCountriesWorkerAr("ar"))
        }


    }
    private fun handleSingleEvent(event: LanguageContract.CountryLocalEvent) {
        // Handle single events
        when(event){
            is LanguageContract.CountryLocalEvent.UpdateTheCountry->{
                val spinnerAdapter = CustomSpinnerAdapter(requireContext(),event.countries)
                binding.spinner.adapter = spinnerAdapter
            }
            is LanguageContract.CountryLocalEvent.NavigateToOnBoarding->{

                findNavController().navigate(R.id.action_languageFragment_to_viewPagerFragment)
            }
            is LanguageContract.CountryLocalEvent.StartCountriesWorker->{
                    updateLocale(event.language)
            }

            is LanguageContract.CountryLocalEvent.ShowWorkerStateToast ->showToast(event.workerState)
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
//    private fun getUserPreference():UserPreference {
//        val preferredCountry = binding.spinner.selectedItem.toString()
//        val language=  AppCompatDelegate.getApplicationLocales()
//        // Create a UserPreference object with the retrieved values
//        return UserPreference(preferredCountry, language[0]?.toLanguageTag().toString())
//        }

}