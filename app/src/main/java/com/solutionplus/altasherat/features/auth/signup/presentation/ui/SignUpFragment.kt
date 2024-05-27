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
import com.solutionplus.altasherat.common.presentation.ui.listener.SharedButtonListener
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(), SharedButtonListener {

    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var countries: List<Country>
    private val customCountiesList: ArrayList<String> = ArrayList()
    private var countryId: Int = 2
    private var countryCode: String = "0020"


    override fun onFragmentReady(savedInstanceState: Bundle?) {

        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = countries[position]
            selectedCountryItem.let { country ->
                countryId = country.id
                countryCode = country.phoneCode
            }

        }

        collectFlowWithLifecycle(signUpViewModel.singleEvent) { event ->
            when (event) {
                is SignUpContract.MainEvent.GetCountries -> {
                    countries = event.countries
                    setCustomCountry()
                    setUpCountryCodeAdapter()
                }

                is SignUpContract.MainEvent.SignUpIsSuccessfully -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        collectFlowWithLifecycle(signUpViewModel.viewState) { result ->
            result.exception?.let {
                if (it.message?.isNotEmpty()!!) {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
            onLoading(result.isLoading)
        }


    }

    override fun viewInit() {
    }


     private fun signUp() {
        val firstname: String = binding.etFirstName.text.toString().trim()
        val lastname: String = binding.etLastName.text.toString().trim()
        val email: String = binding.etEmail.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val passwordConfirmation = binding.etPassword.text.toString().trim()
        val phoneRequest = PhoneSignUpRequest(countryCode, number = phoneNumber)

        val signUpUserRequest = UserSignUpRequest(
            firstname,
            "Ibrahem",
            lastname,
            email,
            password,
            passwordConfirmation,
            countryId,
            phoneRequest
        )

        signUpViewModel.processIntent(SignUpContract.MainAction.SignUp(signUpUserRequest))

    }

    private fun setUpCountryCodeAdapter() {
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.country_menu_item, customCountiesList)
        binding.etCountryCode.setAdapter(arrayAdapter)
    }


    override fun onLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    override fun subscribeToObservables() {

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


