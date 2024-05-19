package com.solutionplus.altasherat.features.splash.presention

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSplachBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplachBinding>() {

    private val viewModel: SplashViewModel by viewModels()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(SplashContract.SplashAction.CheckCountryStringKey)
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            handleViewState(state)
        }

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
    }
    private fun handleViewState(state: SplashContract.SplashViewState) {
        when (state) {
            is SplashContract.SplashViewState.Idle -> {
                // Handle the idle state
            }
            is SplashContract.SplashViewState.Loading -> {
                // Handle the loading state
            }
            is SplashContract.SplashViewState.Success -> {
                // Handle the success state
                showToast("Data loaded successfully")
            }
            is SplashContract.SplashViewState.Error -> {
                // Handle the error state
                val errorMessage = state.message
                showToast("Error: $errorMessage")
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleSingleEvent(event: SplashContract.SplashEvent) {
        // Handle single events
        when (event) {
            SplashContract.SplashEvent.NavigateToHome -> {
                logger.debug("navigate to home")
            }
            SplashContract.SplashEvent.NavigateToOnBoarding -> {
            Intent(requireActivity(), OnboardingActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            }
        }


    }

    override fun viewInit() {
        logger.debug("splash")

    }
    companion object {
        private val logger = getClassLogger()
    }



}