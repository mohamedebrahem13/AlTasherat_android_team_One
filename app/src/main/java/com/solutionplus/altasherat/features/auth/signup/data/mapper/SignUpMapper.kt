package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

object SignUpMapper : Mapper<SignUpResponseDto, UserInfo, SignUpUserEntity>() {
    override fun dtoToDomain(model: SignUpResponseDto): UserInfo {
        return UserInfo(
            token = model.token.orEmpty(),
            username = model.signUpUserDto?.username.orEmpty(),
            firstname = model.signUpUserDto?.firstname.orEmpty(),
            lastname = model.signUpUserDto?.lastname.orEmpty(),
            email = model.signUpUserDto?.email.orEmpty(),
            countryCode = model.signUpUserDto?.signUpPhoneDto?.countryCode.orEmpty(),
            number = model.signUpUserDto?.signUpPhoneDto?.number.orEmpty(),
            message = model.message.orEmpty()
        )
    }

    override fun entityToDomain(model: SignUpUserEntity): UserInfo {
        return UserInfo(
            token = null,
            username = model.username,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            countryCode = model.countryCode,
            number = model.number,
            message = null
        )
    }

    override fun domainToEntity(model: UserInfo): SignUpUserEntity {
        return SignUpUserEntity(
            username = model.username,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            countryCode = model.countryCode,
            number = model.number
        )
    }

}