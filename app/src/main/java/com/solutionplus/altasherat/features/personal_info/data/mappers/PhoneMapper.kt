package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.personal_info.data.models.entity.PhoneEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.Phone

internal object PhoneMapper : Mapper<PhoneDto, Phone, PhoneEntity>() {

    override fun dtoToDomain(model: PhoneDto): Phone {
        return Phone(
            id = model.id ?: -1,
            countryCode = model.countryCode.orEmpty(),
            number = model.number.orEmpty()
        )
    }

    override fun entityToDomain(model: PhoneEntity): Phone {
        return Phone(id = model.id, countryCode = model.countryCode, number = model.number)
    }
}
