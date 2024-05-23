package com.solutionplus.altasherat.features.services.country.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class LinksDto(
    @SerializedName("first")
    val first: String? = null,
    @SerializedName("last")
    val last: String? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null,
)