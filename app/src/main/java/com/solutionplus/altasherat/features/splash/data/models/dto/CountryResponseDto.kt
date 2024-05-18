package com.solutionplus.altasherat.features.splash.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class CountryResponseDto(
    @SerializedName("data") val data: List<CountryDto>?,
    @SerializedName("links") val links: LinksDto?,
    @SerializedName("meta") val meta: MetaDto?
)