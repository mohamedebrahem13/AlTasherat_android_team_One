package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER_VALIDATION
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidationUC
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import javax.inject.Inject

class SignUpUC @Inject constructor(
    private val repository: ISignUpRepository,
    private val userInputsValidationUC: UserInputsValidationUC,
    private val saveUserUC: SaveUserUC
) : BaseUseCase<UserResponse, UserSignUpRequest>() {
    override suspend fun execute(params: UserSignUpRequest?): UserResponse {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UpdateInfoRequest::class,
                message = "Request is null"
            )
        }

        validateRequest(params).takeIf { it.isNotEmpty() }?.let {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UpdateInfoRequest::class,
                errors = it
            )
        }

        val user = params.let {
            //userInputsValidationUC.validateUserInputs(it)
            repository.signup(it)
        }
        saveUserUC.execute(user.user)
        repository.saveUserToken(user.token)
        return user
    }

    private fun validateRequest(request: UserSignUpRequest): Map<String, String> {
        return mutableMapOf<String, String>().apply {
            if (!request.validateFirstName()) put(FIRST_NAME, FIRST_NAME_VALIDATION)
            if (!request.validateLastName()) put(LAST_NAME, LAST_NAME_VALIDATION)
            if (!request.validateEmail()) put(EMAIL, EMAIL_VALIDATION)
            if (!request.validatePassword()) put(PASSWORD, PASSWORD_VALIDATION)
            if (!request.phoneSignUpRequest.validatePhoneNumber()) put(
                PHONE,
                PHONE_NUMBER_VALIDATION
            )
        }
    }
}














