package com.solutionplus.altasherat.features.auth.signup.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.contracts.LoginContracts
import com.solutionplus.altasherat.features.auth.signup.presentation.contracts.SignUpContract
import com.solutionplus.altasherat.features.auth.signup.presentation.contracts.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>() {


    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onFragmentReady(savedInstanceState: Bundle?) {

        val firstName = binding.firstNameEt.text.toString().trim()
        val lastName = binding.lastNameEt.text.toString()
        val email = binding.emailEt.text.toString()
        val phoneNumber = binding.phoneEt.text.toString()
        val countryCode = binding.countryCodeEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val passwordConfirmation = binding.passwordEt.text.toString()
        val countryId = "1"



        val userRequest = UserRequest(
            firstname = "ebram",
            middleName = "zakria",
            lastname = "ibrahem",
            email = "abram@gmail.com",
            password = "123456789",
            passwordConfirmation = "123456789",
            "1",
            phoneRequest = PhoneRequest("0020", number = "12345678989")
        )
        binding.signupBtn.setOnClickListener {
            Toast.makeText(requireContext(), firstName, Toast.LENGTH_LONG).show()

            signUpViewModel.processIntent(SignUpContract.MainAction.SignUp(userRequest))
        }
    }

    override fun viewInit() {}

    private fun EditText.hintDisabled(textInputLayout: TextInputLayout) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.isHintEnabled = s?.isNotEmpty() != true
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })
    }




    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }
}