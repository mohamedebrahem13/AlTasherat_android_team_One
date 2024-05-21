package com.solutionplus.altasherat.features.auth.signup.presentation.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpContract
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpViewModel
import com.solutionplus.altasherat.features.auth.ui.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>() {

    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var authActivity: AuthActivity

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        authActivity.triggerButton {
            signUp()
        }

        collectFlowWithLifecycle(signUpViewModel.viewState) { result ->
            result.exception?.let {
                if (it.message?.isNotEmpty()!!) {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun viewInit() {

    }

    private fun signUp() {
        val firstname: String = binding.firstNameEt.text.toString().trim()
        val lastname: String = binding.lastNameEt.text.toString().trim()
        val email: String = binding.emailEt.text.toString().trim()
        val phoneNumber = binding.phoneEt.text.toString().trim()
        val password = binding.passwordEt.text.toString().trim()
        val passwordConfirmation = binding.passwordEt.text.toString().trim()
        val countryCode = "1"
        val phoneRequest = PhoneSignUpRequest("0020", number = phoneNumber)

        val signUpUserRequest = UserSignUpRequest(
            firstname, "sdadsa", lastname, email, password, passwordConfirmation, countryCode, phoneRequest
        )

        signUpViewModel.processIntent(SignUpContract.MainAction.SignUp(signUpUserRequest))

    }

    override fun onResume() {
        super.onResume()
        authActivity.updateButtonText(getString(R.string.signup_text))

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authActivity = context as AuthActivity
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override fun subscribeToObservables() {
    }

}