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
        val userSignUpRequest = UserSignUpRequest(
            firstname = "ibrahem",
            middleName = "zakria",
            lastname = "aziz",
            email = "ibrahem2@gmail.com",
            password = "123456544789",
            passwordConfirmation = "123456544789",
            "1",
            phoneSignUpRequest = PhoneSignUpRequest("0020", number = "2266989857")
        )

        authActivity.triggerButton {
            signUpViewModel.processIntent(SignUpContract.MainAction.SignUp(userSignUpRequest))
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