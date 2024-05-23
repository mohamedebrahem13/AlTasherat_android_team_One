package com.solutionplus.altasherat.features.personal_info.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personal_info.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.personal_info.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.personal_info.domain.models.Country

internal object CountryMapper : Mapper<CountryDto, Country, CountryEntity>() {
    override fun dtoToDomain(model: CountryDto): Country {
        return Country(
            id = model.id ?: -1,
            name = model.name.orEmpty(),
            phoneCode = model.phoneCode.orEmpty(),
            flag = model.flag.orEmpty(),
            isSelected = false
        )
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