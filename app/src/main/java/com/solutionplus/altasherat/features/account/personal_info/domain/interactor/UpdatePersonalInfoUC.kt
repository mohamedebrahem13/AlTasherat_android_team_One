package com.solutionplus.altasherat.features.account.personal_info.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.MIDDLE_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.MIDDLE_NAME_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_VALIDATION
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class UpdatePersonalInfoUC(
    private val repository: IPersonalInfoRepository,
    private val saveUserUC: SaveUserUC
) : BaseUseCase<Unit, UpdateInfoRequest>() {

    override suspend fun execute(params: UpdateInfoRequest?) {
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

        val result = repository.updatePersonalInfo(params)
        saveUserUC.execute(result.user)
    }

    private fun validateRequest(request: UpdateInfoRequest): Map<String, String> {
        return mutableMapOf<String, String>().apply {
            if (!request.isFirstNameValid()) put(FIRST_NAME, FIRST_NAME_VALIDATION)
            if (!request.isMiddleNameValid()) put(MIDDLE_NAME, MIDDLE_NAME_VALIDATION)
            if (!request.isLastNameValid()) put(LAST_NAME, LAST_NAME_VALIDATION)
            if (!request.isEmailValid()) put(EMAIL, EMAIL_VALIDATION)
            if (!request.isPhoneValid()) put(PHONE, PHONE_VALIDATION)
            if (!request.phone.isNumberValid()) put(PHONE_NUMBER, PHONE_NUMBER_VALIDATION)
        }
    }
}