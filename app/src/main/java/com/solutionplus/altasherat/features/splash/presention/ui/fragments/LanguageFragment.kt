package com.solutionplus.altasherat.features.splash.presention.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageBinding
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.presention.ui.adapter.CustomSpinnerAdapter
import com.solutionplus.altasherat.features.splash.presention.viewmodels.LanguageContract
import com.solutionplus.altasherat.features.splash.presention.viewmodels.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {

    private val viewModel: LanguageViewModel by viewModels()
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        getLocal()
        viewModel.onActionTrigger( LanguageContract.LanguageAction.FetchCountriesFromLocal)
    }

    override fun onLoading(isLoading: Boolean) {}

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            handleViewState(state)
        }
    }
  private fun handleViewState(state: LanguageContract.LanguageViewState){
      when {
          state.selectedCountry != null -> {
              setSpinner( state.selectedCountry)
          }

      }

  }
    private fun setSpinner(selectedCountry: String) {
        // Extract the ID from the selectedCountry string
        val countryId = selectedCountry.substringAfter("id=").substringBefore(",").toIntOrNull()

        // Check if the extracted countryId is not null
        countryId?.let { id ->
            // Retrieve the adapter and countries list
            val adapter = binding.spinner.adapter as? CustomSpinnerAdapter
            val countries = adapter?.countries

            // Iterate through the countries list to find the matching country by ID
            countries?.forEachIndexed { index, country ->
                if (country.id == id) {
                    // If the country's ID matches id,
                    // set the spinner selection to the corresponding position
                    binding.spinner.setSelection(index)
                    return@forEachIndexed
                }
            }
        }
    }


    override fun viewInit() {

        binding.buttonContinue.setOnClickListener {
            val preferredCountry = binding.spinner.selectedItem.toString()
            viewModel.onActionTrigger(LanguageContract.LanguageAction.NextButtonClick(preferredCountry))
        }

        binding.radioButton2.setOnClickListener {
            viewModel.onActionTrigger(LanguageContract.LanguageAction.StartCountriesWorker("en"))

        }

        binding.radioButton1.setOnClickListener {
            viewModel.onActionTrigger(LanguageContract.LanguageAction.StartCountriesWorker("ar"))

        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = parent?.getItemAtPosition(position) as? Country
                val selectedCountryName = selectedCountry?.name ?: ""
                logger.debug("selectedspinner: $selectedCountryName")
                viewModel.onActionTrigger(LanguageContract.LanguageAction.SpinnerClicked(selectedCountryName))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }


    }
    private fun handleSingleEvent(event: LanguageContract.LanguageEvent) {
        // Handle single events
        when(event){
            is LanguageContract.LanguageEvent.UpdateTheCountry->{
                val spinnerAdapter = CustomSpinnerAdapter(requireContext(),event.countries)
                binding.spinner.adapter = spinnerAdapter
            }
            is LanguageContract.LanguageEvent.NavigateToOnBoarding->{

                findNavController().navigate(R.id.action_languageFragment_to_viewPagerFragment)
            }
            is LanguageContract.LanguageEvent.StartCountriesWorker->{
                    updateLocale(event.language)
            }

            is LanguageContract.LanguageEvent.ShowWorkerStateToast ->showToast(event.workerState)
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

    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }
    companion object {
        private val logger = getClassLogger()
    }

}