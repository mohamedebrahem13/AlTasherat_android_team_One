package com.solutionplus.altasherat.features.auth.login.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginUserDto
import com.solutionplus.altasherat.features.auth.login.data.models.entity.LoginUserEntity
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo

object LoginUserMapper: Mapper<LoginUserDto, LoginUserInfo, LoginUserEntity>() {

    override fun dtoToDomain(model: LoginUserDto): LoginUserInfo {
        return LoginUserInfo(
            username = model.username.orEmpty(),
            firstname = model.firstname.orEmpty(),
            lastname = model.lastname.orEmpty(),
            email = model.email.orEmpty(),
            countryCode = model.phone?.countryCode.orEmpty(),
            number = model.phone?.number.orEmpty(),
        )
    }

    override fun entityToDomain(model: LoginUserEntity): LoginUserInfo {
        return LoginUserInfo(
            username = model.username,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            countryCode = model.countryCode,
            number = model.number,
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