package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.services.user.data.models.entity.PhoneEntity
import com.solutionplus.altasherat.features.services.user.domain.models.Phone

internal object PhoneMapper : Mapper<PhoneDto, Phone, PhoneEntity>() {

    override fun dtoToDomain(model: PhoneDto): Phone {
        return Phone(
            countryCode = model.countryCode.orEmpty(),
            extension = model.extension.orEmpty(),
            holderName = model.holderName.orEmpty(),
            id = model.id ?: -1,
            number = model.number.orEmpty(),
            type = model.type.orEmpty()
        )
    }

    override fun entityToDomain(model: PhoneEntity): Phone {
        return Phone(
            id = model.id,
            countryCode = model.countryCode,
            number = model.number,
            extension = model.extension,
            type = model.type,
            holderName = model.holderName
        )
    }

    override fun domainToEntity(model: Phone): PhoneEntity {
        return PhoneEntity(
            id = model.id,
            countryCode = model.countryCode,
            number = model.number,
            extension = model.extension,
            type = model.type,
            holderName = model.holderName
        )
    }
}