package com.solutionplus.altasherat.features.auth.login.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.entity.LoginUserEntity
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo

object LoginMapper: Mapper<LoginResponseDto, LoginUserInfo, LoginUserEntity>() {
    override fun dtoToDomain(model: LoginResponseDto): LoginUserInfo {
        return LoginUserInfo(
            token = model.token ?: "",
            username = model.loginUserDto?.username.orEmpty(),
            firstname = model.loginUserDto?.firstname.orEmpty(),
            lastname = model.loginUserDto?.lastname.orEmpty(),
            email = model.loginUserDto?.email.orEmpty(),
            countryCode = model.loginUserDto?.phone?.countryCode.orEmpty(),
            number = model.loginUserDto?.phone?.number.orEmpty(),
            message = model.message.orEmpty()
        )
    }

    override fun entityToDomain(model: LoginUserEntity): LoginUserInfo {
        return LoginUserInfo(
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

    override fun domainToEntity(model: LoginUserInfo): LoginUserEntity {
        return LoginUserEntity(
            username = model.username,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            countryCode = model.countryCode,
            number = model.number
        )
    }
}