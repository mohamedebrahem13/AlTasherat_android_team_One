package com.solutionplus.altasherat.features.auth.login.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneLoginRequest
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginViewModel
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginContracts
import com.solutionplus.altasherat.features.auth.signup.presentation.ui.SignUpFragment.Companion.logger
import com.solutionplus.altasherat.features.auth.ui.CountryCodeAdapter
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.auth.viewmodel.SharedViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginSignupButtonListener {

    private val loginViewMode by viewModels<LoginViewModel>()
    private lateinit var countries: List<Country>
    private lateinit var adapter: CountryCodeAdapter
    private lateinit var countryId: String
    private lateinit var countryCode: String

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel.setUpListener(this)
        viewModel.setButtonText(getString(R.string.login_text))

        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = adapter.getItem(position)
            selectedCountryItem?.let { country ->
                countryId = country.id.toString()
                countryCode = country.phoneCode
                logger.debug(country.toString())
            }
        }

        collectFlowWithLifecycle(loginViewMode.singleEvent) { event ->
            when(event) {
                is LoginContracts.MainEvent.GetCountries -> {
                    countries = event.countries
                    setUpCountryCodeAdapter()
                }
                is LoginContracts.MainEvent.LoginIsSuccessfully -> Unit
            }
        }

        collectFlowWithLifecycle(loginViewMode.viewState) { result ->
            result.exception?.let {
                if (it.message?.isNotEmpty()!!) {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {

    }

    private fun login() {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val countryCode = binding.etCountryCode.text.toString()
        val password = binding.etPassword.text.toString()
        val phoneRequest = PhoneLoginRequest(countryCode, phoneNumber)
        val userLoginRequest = UserLoginRequest(
            password = password,
            phoneLoginRequest = phoneRequest
        )
        loginViewMode.processIntent(LoginContracts.MainAction.Login(userLoginRequest))

    }

    private fun setUpCountryCodeAdapter() {
        adapter = CountryCodeAdapter(requireContext(), countries)
        binding.etCountryCode.setAdapter(adapter)
    }


    override fun triggerButton() {
        login()
    }
}