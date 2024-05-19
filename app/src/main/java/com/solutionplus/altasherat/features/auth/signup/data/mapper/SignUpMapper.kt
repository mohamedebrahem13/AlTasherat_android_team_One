package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.UserResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

object SignUpMapper : Mapper<UserResponseDto, UserInfo, SignUpUserEntity>() {
    override fun dtoToDomain(model: UserResponseDto): UserInfo {
        return UserInfo(
            token = model.token.orEmpty(),
            username = model.user?.username.orEmpty(),
            firstname = model.user?.firstname.orEmpty(),
            lastname = model.user?.lastname.orEmpty(),
            email = model.user?.email.orEmpty(),
            countryCode = model.user?.phone?.countryCode.orEmpty(),
            number = model.user?.phone?.number.orEmpty(),
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