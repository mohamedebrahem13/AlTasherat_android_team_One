package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import javax.inject.Inject

class SignUpUC @Inject constructor(
    private val repository: ISignUpRepository,
    private val saveUserUC: SaveUserUC
) : BaseUseCase<UserResponse, UserSignUpRequest>() {
    override suspend fun execute(params: UserSignUpRequest?): UserResponse {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UserSignUpRequest::class,
                message = "Request is null"
            )
        }

        validateRequest(params).takeIf { it.isNotEmpty() }?.let {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UserSignUpRequest::class,
                errors = it
            )
        }

        val user = params.let {
            repository.signup(it)
        }
        saveUserUC.execute(user.user)
        repository.saveUserToken(user.token)
        return user
    }

    private fun validateRequest(request: UserSignUpRequest): Map<String, Int> {
        return mutableMapOf<String, Int>().apply {
            if (!request.validateFirstName()) put(FIRST_NAME, R.string.first_name_validation)
            if (!request.validateLastName()) put(LAST_NAME, R.string.last_name_validation)
            if (!request.validateEmail()) put(EMAIL, R.string.email_validation)
            if (!request.validatePassword()) put(PASSWORD, R.string.password_validation)
            if (!request.phoneSignUpRequest.validatePhoneNumber()) put(
                PHONE,
                R.string.phone_number_validation
            )
        }
    }
}














