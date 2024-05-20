package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageBinding
import com.solutionplus.altasherat.features.splash.domain.models.Country
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
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_languageFragment_to_onBoardingOneFragment2)
        }

    }
    private fun handleSingleEvent(event: CountryLocalContract.CountryLocalEvent) {
        // Handle single events
        when(event){
            is CountryLocalContract.CountryLocalEvent.UpdateTheCountry->{
                val spinnerAdapter = CustomSpinnerAdapter(requireContext(),event.countries)
                binding.spinner.adapter = spinnerAdapter
            }

        }


    }
    companion object {
        private val logger = getClassLogger()
    }
}