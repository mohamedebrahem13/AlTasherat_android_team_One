package com.solutionplus.altasherat.features.auth.login.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

object LoginMapper: Mapper<LoginResponseDto, LoginUserInfo, Nothing>() {
    override fun dtoToDomain(model: LoginResponseDto): LoginUserInfo {
        return LoginUserInfo(
            token = model.token ?: "",
            username = model.user?.username ?: "",
            firstname = model.user?.firstname ?: "",
            lastname = model.user?.lastname ?: "",
            email = model.user?.email ?: "",
            countryCode = model.user?.phone?.countryCode ?: "",
            number = model.user?.phone?.number ?: "",
            message = model.message ?: ""
        )
    }
}