package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.entity.PhoneEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.Phone

internal object PhoneMapper : Mapper<Unit, Phone, PhoneEntity>() {
    override fun dtoToDomain(model: Unit): Phone {
        throw NotImplementedError("not implemented")
    }

    override fun entityToDomain(model: PhoneEntity): Phone {
        return Phone(id = model.id, countryCode = model.countryCode, number = model.number)
    }
}
