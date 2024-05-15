package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger


import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSplachBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplachBinding>() {

    private val viewModel: SplashViewModel by viewModels()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(CountriesContract.Action.FetchAndSaveCountries)
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            handleViewState(state)
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent()
        }
    }
    private fun handleViewState(state: CountriesContract.SplashViewState) {
        when (state) {
            is CountriesContract.SplashViewState.Idle -> {
                // Handle the idle state
            }
            is CountriesContract.SplashViewState.Loading -> {
                // Handle the loading state
            }
            is CountriesContract.SplashViewState.Success -> {
                // Handle the success state
                showToast("Data loaded successfully")
            }
            is CountriesContract.SplashViewState.Error -> {
                // Handle the error state
                val errorMessage = state.message
                showToast("Error: $errorMessage")
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleSingleEvent() {
        // Handle single events
        findNavController().navigate(R.id.action_splashFragment_to_onBoardingOneFragment)

    }

    override fun viewInit() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        logger.debug("splash")

    }
    companion object {
        private val logger = getClassLogger()
    }



}