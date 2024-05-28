package com.solutionplus.altasherat.features.auth.login.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.ResponseDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper

internal object LoginMapper: Mapper<ResponseDto, LoginUserResponse, Unit>() {

    override fun dtoToDomain(model: ResponseDto): LoginUserResponse {
        return LoginUserResponse(
            token = model.token.orEmpty(),
            user = UserMapper.dtoToDomain(model.userDto ?: UserDto()),
            message = model.message.orEmpty()
        )
    }
}