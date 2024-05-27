package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.user.data.models.entity.PhoneEntity
import com.solutionplus.altasherat.features.services.user.domain.models.Phone

internal object PhoneMapper : Mapper<Unit, Phone, PhoneEntity>() {
    override fun dtoToDomain(model: Unit): Phone {
        throw NotImplementedError("not implemented")
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
}