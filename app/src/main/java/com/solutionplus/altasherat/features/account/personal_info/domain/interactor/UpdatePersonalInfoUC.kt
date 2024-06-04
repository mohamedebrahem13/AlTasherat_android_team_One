package com.solutionplus.altasherat.features.account.personal_info.domain.interactor

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.EMAIL
import com.solutionplus.altasherat.common.domain.constants.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.LAST_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.MIDDLE_NAME
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE
import com.solutionplus.altasherat.common.domain.constants.Constants.PHONE_NUMBER
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

    private fun validateRequest(request: UpdateInfoRequest): Map<String, Int> {
        return mutableMapOf<String, Int>().apply {
            if (!request.isFirstNameValid()) put(FIRST_NAME, R.string.first_name_validation)
            if (!request.isMiddleNameValid()) put(MIDDLE_NAME, R.string.middle_name_validation)
            if (!request.isLastNameValid()) put(LAST_NAME, R.string.last_name_validation)
            if (!request.isEmailValid()) put(EMAIL, R.string.email_validation)
            if (!request.isPhoneValid()) put(PHONE, R.string.phone_validation)
            if (!request.phone.isNumberValid()) put(PHONE_NUMBER, R.string.phone_number_validation)
        }
    }
}