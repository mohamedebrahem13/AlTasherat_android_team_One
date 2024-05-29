package com.solutionplus.altasherat.features.auth.signup.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpContract
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpViewModel
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(), LoginSignupButtonListener {

    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var countries: List<Country>
    private val customCountiesList: ArrayList<String> = ArrayList()
    private var countryId: Int? = null
    private var countryCode: String? = null


    override fun onFragmentReady(savedInstanceState: Bundle?) {

        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries[position]
            selectedCountryItem.let { country ->
                countryId = country.id
                countryCode = country.phoneCode
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

            signUpViewModel.processIntent(SignUpContract.SignUpAction.SignUp(signUpUserRequest))
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

        collectFlowWithLifecycle(signUpViewModel.singleEvent) { event ->
            when (event) {
                is SignUpContract.SignUpEvent.GetCountries -> {
                    countries = event.countries
                    countryId = countries[0].id
                    countryCode = countries[0].phoneCode
                    setCustomCountry()
                    setUpCountryCodeAdapter()
                }

                is SignUpContract.SignUpEvent.SignUpIsSuccessfully -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        collectFlowWithLifecycle(signUpViewModel.viewState) { result ->
            result.exception?.let {
                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
            onLoading(result.isLoading)
        }
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
        signUp()
    }

    companion object {
        val logger = getClassLogger()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}


