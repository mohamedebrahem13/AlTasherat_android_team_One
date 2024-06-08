package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class LoginWithPhoneUC(
    private val repository: ILoginRepository,
    private val saveUserUC: SaveUserUC,
) : BaseUseCase<LoginUserResponse, UserLoginRequest>() {

    override suspend fun execute(params: UserLoginRequest?): LoginUserResponse {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UserLoginRequest::class,
                message = "Request is null"
            )
        }

        validateRequest(params).takeIf { it.isNotEmpty() }?.let {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UserLoginRequest::class,
                errors = it
            )
        }

        val loginUserResponse = params.let {
            repository.loginWithPhone(it)
        }
        saveUserUC.execute(loginUserResponse.user)
        repository.saveUserToken(loginUserResponse.token)
        return loginUserResponse
    }

    private fun validateRequest(request: UserLoginRequest): Map<String, Int> {
        return mutableMapOf<String, Int>().apply {
            if (!request.phoneLoginRequest.validatePhoneNumber()) put(
                PHONE_NUMBER,
                R.string.phone_number_validation
            )
            if (!request.validatePassword()) put(PASSWORD, R.string.password_validation)
        }
    }
}