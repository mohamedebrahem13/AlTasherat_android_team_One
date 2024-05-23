package com.solutionplus.altasherat.features.services.country.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class LinkDto(
    @SerializedName("active")
    val active: Boolean? = null,
    @SerializedName("label")
    val label: String? = null,
    @SerializedName("url")
    val url: String? = null
)