package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.UserResponseDto
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

object SignUpMapper: Mapper<UserResponseDto, UserInfo, Nothing>() {
    override fun dtoToDomain(model: UserResponseDto): UserInfo {
        return UserInfo(
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