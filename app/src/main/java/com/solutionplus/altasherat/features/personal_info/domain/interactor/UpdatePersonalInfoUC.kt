package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository

class UpdatePersonalInfoUC(
    private val repository: IPersonalInfoRepository
) : BaseUseCase<UpdateInfo, UpdateInfoRequest>() {

    override suspend fun execute(params: UpdateInfoRequest?): UpdateInfo {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UpdateInfoRequest::class,
                message = "Request is null"
            )
        }

        validateRequest(params)?.let { message ->
            throw AlTasheratException.Local.RequestValidation(
                clazz = UpdateInfoRequest::class,
                message = message
            )
        }

        return repository.updatePersonalInfo(params)
    }

    private fun validateRequest(request: UpdateInfoRequest): String? {
        return request.run {
            when {
                !isFirstNameValid() -> "First name is not valid"
                !isMiddleNameValid() -> "Middle name is not valid"
                !isLastNameValid() -> "Last name is not valid"
                !isEmailValid() -> "Email is not valid"
                !isBirthDateValid() -> "Birth date is not valid"
                !isPhoneValid() -> "Phone is not valid"
                !isCountryIdValid() -> "Country is not valid"
                else -> null
            }
        }
    }
}