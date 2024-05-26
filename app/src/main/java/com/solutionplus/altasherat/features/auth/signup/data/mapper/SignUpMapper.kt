package com.solutionplus.altasherat.features.auth.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse

object SignUpMapper : Mapper<SignUpResponseDto, UserResponse, Unit>() {

    override fun dtoToDomain(model: SignUpResponseDto): UserResponse {
        return UserResponse(
            token = model.token.orEmpty(),
            user = model.signUpUserDto?.let { UserMapper.dtoToDomain(it) }!!,
            message = model.message.orEmpty()
        )
    }
}