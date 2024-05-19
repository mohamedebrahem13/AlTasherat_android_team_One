package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.Country

internal object CountryMapper : Mapper<Unit, Country, CountryEntity>() {
    override fun dtoToDomain(model: Unit): Country {
        throw NotImplementedError("not implemented")
    }

    override fun entityToDomain(model: CountryEntity): Country {
        return Country(
            id = model.id,
            name = model.name,
            phoneCode = model.phoneCode,
            flag = model.flag,
            isSelected = false
        )
    }
}