package com.solutionplus.altasherat.features.auth.login.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneLoginRequest
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginViewModel
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginContracts
import com.solutionplus.altasherat.features.auth.presentation.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginSignupButtonListener {

    private val loginViewMode by viewModels<LoginViewModel>()
    private lateinit var countries: List<Country>
    private val customCountiesList: ArrayList<String> = ArrayList()
    private var countryCode: String = "0020"

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries[position]
            selectedCountryItem.let { country ->
                countryCode = country.phoneCode
            }

        }
    }

    override fun onLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(loginViewMode.singleEvent) { event ->
            when(event) {
                is LoginContracts.LoginEvent.GetCountries -> {
                    countries = event.countries
                    setCustomCountry()
                    setUpCountryCodeAdapter()
                }
                is LoginContracts.LoginEvent.LoginIsSuccessfully -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        collectFlowWithLifecycle(loginViewMode.viewState) { result ->
            result.exception?.let {
                if (it.message?.isNotEmpty()!!) {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
            onLoading(result.isLoading)
        }
    }

    override fun viewInit() {}

     fun login() {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val phoneRequest = PhoneLoginRequest(countryCode, phoneNumber)
        val userLoginRequest = UserLoginRequest(
            password = password,
            phoneLoginRequest = phoneRequest
        )
        loginViewMode.processIntent(LoginContracts.LoginAction.Login(userLoginRequest))

    }

    private fun setUpCountryCodeAdapter() {
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.country_menu_item, customCountiesList)
        binding.etCountryCode.setAdapter(arrayAdapter)
    }

    private fun setCustomCountry() {
        countries.forEach {
            val formattedCountryCode = requireContext().getString(
                R.string.selected_country_code,
                it.flag,
                it.phoneCode.substring(2)
            )
            customCountiesList.add(formattedCountryCode)
        }
    }

    override fun triggerButton() {
        login()
    }


    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
