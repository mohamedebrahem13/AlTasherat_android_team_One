package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.personal_info.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personal_info.domain.models.UpdateInfo

internal object UpdateInfoMapper : Mapper<UpdateInfoResponseDto, UpdateInfo, Unit>() {
    override fun dtoToDomain(model: UpdateInfoResponseDto): UpdateInfo {
        return UpdateInfo(
            message = model.message.orEmpty(),
            user = UserMapper.dtoToDomain(model.user ?: UserDto())
        )
    }
}