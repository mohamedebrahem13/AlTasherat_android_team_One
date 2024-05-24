package com.solutionplus.altasherat.features.auth.signup.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpContract
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpViewModel
import com.solutionplus.altasherat.features.auth.ui.CountryCodeAdapter
import com.solutionplus.altasherat.features.auth.ui.listener.LoginSignupButtonListener
import com.solutionplus.altasherat.features.auth.viewmodel.SharedViewModel
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(), LoginSignupButtonListener {

    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var countries: List<Country>
    private lateinit var adapter: CountryCodeAdapter
    private lateinit var countryId: String
    private lateinit var countryCode: String


    override fun onFragmentReady(savedInstanceState: Bundle?) {

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel.setUpListener(this)
        viewModel.setButtonText(getString(R.string.signup_text))

        binding.etCountryCode.setOnItemClickListener { _, _, position, _ ->
            val selectedCountryItem = adapter.getItem(position)
            selectedCountryItem?.let { country ->
                countryId = country.id.toString()
                countryCode = country.phoneCode
            }

        }

        collectFlowWithLifecycle(signUpViewModel.singleEvent) { event ->
            when (event) {
                is SignUpContract.MainEvent.GetCountries -> {
                    countries = event.countries
                    setUpCountryCodeAdapter()
                }

                is SignUpContract.MainEvent.SignUpIsSuccessfully -> Unit
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
        adapter = CountryCodeAdapter(requireContext(), countries)
        binding.etCountryCode.setAdapter(adapter)
    }


    override fun onLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    override fun subscribeToObservables() {

    }

    override fun triggerButton() {
        signUp()
    }

    companion object {
        val logger = getClassLogger()
    }

}


