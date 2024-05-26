package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpUserDto
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

object UserMapper: Mapper<SignUpUserDto, UserInfo, SignUpUserEntity>() {


    override fun dtoToDomain(model: SignUpUserDto): UserInfo {
        return UserInfo(
            username = model.username.orEmpty(),
            firstname = model.firstname.orEmpty(),
            lastname = model.lastname.orEmpty(),
            email = model.email.orEmpty(),
            countryCode = model.signUpPhoneDto?.countryCode.orEmpty(),
            number = model.signUpPhoneDto?.number.orEmpty(),
        )
    }

    override fun entityToDomain(model: SignUpUserEntity): UserInfo {
        return UserInfo(
            username = model.username,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            countryCode = model.countryCode,
            number = model.number,
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