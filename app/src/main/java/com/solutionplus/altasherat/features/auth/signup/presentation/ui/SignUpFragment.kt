package com.solutionplus.altasherat.features.auth.signup.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.showShortToast
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.auth.presentation.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpContract
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpViewModel
import com.solutionplus.altasherat.features.home.presentation.HomeActivity
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(), LoginSignupButtonListener {

    private val viewModel by viewModels<SignUpViewModel>()
    private var countries: List<Country>? = null
    private val customCountiesList: ArrayList<String> = ArrayList()
    private var countryId: Int? = null
    private var countryCode: String? = null


    override fun onFragmentReady(savedInstanceState: Bundle?) {

        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries?.get(position)
            selectedCountryItem.let { country ->
                countryId = country?.id
                countryCode = country?.phoneCode
            }

        }
    }

    override fun viewInit() {
    }


    private fun signUp() {
        countryId?.let { id ->
            val firstname: String = binding.etFirstName.text.toString().trim()
            val lastname: String = binding.etLastName.text.toString().trim()
            val email: String = binding.etEmail.text.toString().trim()
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val passwordConfirmation = binding.etPassword.text.toString().trim()
            val phoneRequest = PhoneSignUpRequest(countryCode = countryCode!!, number = phoneNumber)

            val signUpUserRequest = UserSignUpRequest(
                firstname,
                middleName = "",
                lastname,
                email,
                password,
                passwordConfirmation,
                id,
                phoneRequest
            )

            viewModel.processIntent(SignUpContract.SignUpAction.SignUp(signUpUserRequest))
        }

    }

    private fun setUpCountryCodeAdapter() {
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.country_menu_item, customCountiesList)
        binding.etCountryCode.setAdapter(arrayAdapter)
    }


    override fun onLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    override fun subscribeToObservables() {

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            when (event) {
                is SignUpContract.SignUpEvent.GetCountries -> {
                    if (event.countries?.isNotEmpty() == true) {
                        countries = event.countries
                        countryId = countries!![0].id
                        countryCode = countries!![0].phoneCode
                        setCustomCountry()
                        setUpCountryCodeAdapter()
                    }
                }

                is SignUpContract.SignUpEvent.SignUpIsSuccessfully -> {
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
                handleException(exception, ::handleValidationErrors)
            }
        }
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
        signUp()
    }

    companion object {
        val logger = getClassLogger()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun handleValidationErrors(errors: Map<String, String>) {
        val errorFields = mapOf(
            FIRST_NAME to binding.inputFirstName,
            LAST_NAME to binding.inputLastName,
            EMAIL to binding.inputEmailName,
            PHONE to binding.inputPhoneNumber,
            PASSWORD to binding.inputPasswordName
        )

        errorFields.forEach { (key, field) ->
            field.error = errors[key]
            field.editText?.doAfterTextChanged { field.error = null }
        }
    }
}


