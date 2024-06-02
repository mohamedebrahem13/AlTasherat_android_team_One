package com.solutionplus.altasherat.features.account.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.account.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.account.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto

internal object UpdateInfoMapper : Mapper<UpdateInfoResponseDto, UpdateInfo, Unit>() {
    override fun dtoToDomain(model: UpdateInfoResponseDto): UpdateInfo {
        return UpdateInfo(
            message = model.message.orEmpty(),
            user = UserMapper.dtoToDomain(model.user ?: UserDto())
        )
    }
}