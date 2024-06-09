package com.solutionplus.altasherat.features.auth.login.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.showShortToast
import com.solutionplus.altasherat.android.extentions.showSnackBar
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginContracts
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginViewModel
import com.solutionplus.altasherat.features.auth.presentation.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.home.presentation.HomeActivity
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginSignupButtonListener {

    private val viewModel by viewModels<LoginViewModel>()
    private var countries: List<Country>? = null
    private val customCountiesList: ArrayList<String> = ArrayList()
    private var countryCode: String? = null

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries?.get(position)
            selectedCountryItem.let { country ->
                countryCode = country?.phoneCode
            }
        }

        binding.textForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_authViewPagerFragment_to_fragmentViewPagerResetPassword)
        }
    }

    override fun onLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is LoginContracts.LoginEvent.GetCountries -> {
                    if (event.countries?.isNotEmpty() == true) {
                        countries = event.countries
                        countryCode = countries!![0].phoneCode
                        setCustomCountry()
                        setUpCountryCodeAdapter()
                    }

                }

                is LoginContracts.LoginEvent.LoginIsSuccessfully -> {
                    requireContext().showShortToast(event.message)
                    Intent(requireActivity(), HomeActivity::class.java).also { intent ->
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
        }

        collectFlowWithLifecycle(viewModel.viewState) { state ->
            onLoading(state.isLoading)

            state.exception?.let { exception ->
                handleException(
                    exception = exception,
                    action = state.action,
                    handleValidationErrors = ::handleValidationErrors
                )
            }
        }
    }

    override fun viewInit() {}

    fun login() {
        countryCode?.let { code ->
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val phoneRequest = PhoneLoginRequest(code, phoneNumber)
            val userLoginRequest = UserLoginRequest(
                password = password,
                phoneLoginRequest = phoneRequest
            )
            viewModel.processIntent(LoginContracts.LoginAction.Login(userLoginRequest))

        }

    }

    private fun setUpCountryCodeAdapter() {
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.country_menu_item, customCountiesList)
        binding.etCountryCode.setAdapter(arrayAdapter)
    }

    private fun setCustomCountry() {
        countries?.forEach {
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

    private fun handleValidationErrors(errors: Map<String, String>) {
        val errorFields = mapOf(
            PHONE_NUMBER to binding.inputPhoneNumber,
            PASSWORD to binding.inputPasswordName
        )

        errorFields.forEach { (key, field) ->
            field.error = errors[key]
            field.editText?.doAfterTextChanged { field.error = null }
        }
    }

    override fun onRetryAction(action: ViewAction?, message: String) {
        showSnackBar(message) {
            action?.let { viewModel.processIntent(it as LoginContracts.LoginAction) }
        }
    }
}
