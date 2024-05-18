package com.solutionplus.altasherat.features.splash.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country

internal object CountryMapper : Mapper<CountryDto, Country, CountryEntity>() {


    override fun dtoToDomain(model: CountryDto): Country {
        return Country(
            id = model.id ?: 0,
            name = model.name ?: "",
            nationality = model.nationality ?: "",
            currency = model.currency ?: "",
            code = model.code ?: "",
            phoneCode = model.phoneCode ?: "",
            visible = model.visible ?: false,
            flag = model.flag ?: ""
        )
    }
    override fun domainToEntity(model: Country): CountryEntity {
        return CountryEntity(
            id = model.id,
            name = model.name,
            nationality = model.nationality,
            currency = model.currency,
            code = model.code,
            phoneCode = model.phoneCode,
            visible = model.visible,
            flag = model.flag
        )
    }

    override fun entityToDomain(model: CountryEntity): Country {
        return Country(
            id = model.id,
            name = model.name,
            nationality = model.nationality,
            currency = model.currency,
            code = model.code,
            phoneCode = model.phoneCode,
            visible = model.visible,
            flag = model.flag
        )
    }

    fun mapEntityListToDomain(entityList: List<CountryEntity>): List<Country> {
        return entityList.map { entityToDomain(it) }
    }
    fun mapDtoToDomain(dto: List<CountryDto>): CountriesResponse {
        val countries = dto.map { dtoToDomain(it)
        }
        return CountriesResponse(countries)
    }
}