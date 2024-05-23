package com.solutionplus.altasherat.features.services.country.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class CountriesDto(
    @SerializedName("data")
    val data: List<CountryDto>? = null,
    @SerializedName("links")
    val links: LinksDto? = null,
    @SerializedName("meta")
    val metaDto: MetaDto? = null
)