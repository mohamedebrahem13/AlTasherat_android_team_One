package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto

internal object SignUpMapper : Mapper<SignUpResponseDto, UserResponse, Unit>() {

    override fun dtoToDomain(model: SignUpResponseDto): UserResponse {
        return UserResponse(
            token = model.token.orEmpty(),
            user = UserMapper.dtoToDomain(model.userDto ?: UserDto()),
            message = model.message.orEmpty()
        )
    }
}