package com.solutionplus.altasherat.features.splash.presention.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSplashBinding
import com.solutionplus.altasherat.features.home.presentation.HomeActivity
import com.solutionplus.altasherat.features.splash.presention.ui.activity.OnboardingActivity
import com.solutionplus.altasherat.features.splash.presention.viewmodels.SplashContract
import com.solutionplus.altasherat.features.splash.presention.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(SplashContract.SplashAction.CheckHasCountriesKey)
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
        when {
            state.isLoading -> {
            }

            state.exception != null -> {
                val errorMessage = state.exception.message ?: "Unknown error"
                showToast("Error: $errorMessage")
            }

            else -> {
                showToast("Data loaded successfully")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleSingleEvent(event: SplashContract.SplashEvent) {
        when (event) {
            SplashContract.SplashEvent.NavigateToHome -> {
                logger.debug("navigate to NavigateToHome")
                Intent(requireActivity(), HomeActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            SplashContract.SplashEvent.NavigateToLanguage -> {
                findNavController().navigate(R.id.action_splash_to_languageFragment)

            }
            SplashContract.SplashEvent.NavigateToOnBoarding ->
                Intent(requireActivity(), OnboardingActivity::class.java).also { intent ->
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
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