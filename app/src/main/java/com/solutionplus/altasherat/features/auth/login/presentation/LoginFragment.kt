package com.solutionplus.altasherat.features.auth.login.presentation

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.auth.signup.presentation.contracts.LoginContracts
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewMode by viewModels<LoginViewModel>()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val loginRequest = LoginRequest("abram@gmail.com", "123456789",PhoneRequest("0020", "12345678989"))
        binding.loginBtn.setOnClickListener {
            loginViewMode.processIntent(LoginContracts.MainAction.Login(loginRequest))
        }
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {

    }
}