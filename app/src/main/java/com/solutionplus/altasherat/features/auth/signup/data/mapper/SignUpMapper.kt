package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

object SignUpMapper : Mapper<SignUpResponseDto, UserInfo, SignUpUserEntity>() {
    override fun dtoToDomain(model: SignUpResponseDto): UserInfo {
        return UserInfo(
            token = model.token.orEmpty(),
            username = model.signUpUserResponse?.username.orEmpty(),
            firstname = model.signUpUserResponse?.firstname.orEmpty(),
            lastname = model.signUpUserResponse?.lastname.orEmpty(),
            email = model.signUpUserResponse?.email.orEmpty(),
            countryCode = model.signUpUserResponse?.signUpPhoneResponse?.countryCode.orEmpty(),
            number = model.signUpUserResponse?.signUpPhoneResponse?.number.orEmpty(),
            message = model.message.orEmpty()
        )
    }

    override fun entityToDomain(model: SignUpUserEntity): UserInfo {
        return UserInfo(
            token = model.token,
            username = model.username,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            countryCode = model.countryCode,
            number = model.number,
            message = null
        )
    }

}