package com.solutionplus.altasherat.features.auth.login.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse

object LoginMapper: Mapper<LoginResponseDto, LoginUserResponse, Unit>() {

    override fun dtoToDomain(model: LoginResponseDto): LoginUserResponse {
        return LoginUserResponse(
            token = model.token.orEmpty(),
            user = model.loginUserDto?.let { LoginUserMapper.dtoToDomain(it) }!!,
            message = model.message.orEmpty()
        )
    }
}