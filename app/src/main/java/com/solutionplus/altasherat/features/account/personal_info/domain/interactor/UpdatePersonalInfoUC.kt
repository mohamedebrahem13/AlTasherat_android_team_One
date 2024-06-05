package com.solutionplus.altasherat.features.account.personal_info.domain.interactor

import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import java.io.File

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

        val requestBody = createMapRequest(params)
        val result = repository.updatePersonalInfo(requestBody.first, requestBody.second)
        getClassLogger().debug(result.toString())
        saveUserUC.execute(result.user)

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

    private fun createMapRequest(request: UpdateInfoRequest): Pair<Map<String, String>, Map<String, List<File>>> {

        // requestBody
        val requestMap: MutableMap<String, String> = mutableMapOf()
        requestMap["firstname"] = request.firstname
        requestMap["lastname"] = request.lastname
        requestMap["middlename"] = request.middlename
        requestMap["email"] = request.email
        requestMap["birthdate"] = request.birthDate
        requestMap["number"] = request.phone.number
        requestMap["country_code"] = request.phone.countryCode
        requestMap["country"] = request.countryId.toString()

        //Files
        val filesMap: MutableMap<String, List<File>> = mutableMapOf()
        filesMap["image"] = listOf(request.image)

        return Pair(requestMap, filesMap)
    }
}


