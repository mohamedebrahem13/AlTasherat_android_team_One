package com.solutionplus.altasherat.features.auth.signup.presentation.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel.SignUpViewModel
import com.solutionplus.altasherat.features.auth.ui.ViewPagerAdapter
import com.solutionplus.altasherat.features.auth.ui.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>() {
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var viewPagerFragment: ViewPagerFragment
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val userRequest = UserRequest(
            firstname = "toto",
            middleName = "zakria",
            lastname = "toto",
            email = "toto@gmail.com",
            password = "123456789",
            passwordConfirmation = "123456789",
            "1",
            phoneRequest = PhoneRequest("0020", number = "123400023389")
        )
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
        viewPagerFragment.updateButtonText(getString(R.string.signup_text))
        viewPagerFragment.updateButtonIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_signup
            )
        )
    }

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