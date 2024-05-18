package com.solutionplus.altasherat.features.splash.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class LinkDto(
    @SerializedName("url") val url: String?,
    @SerializedName("label") val label: String?,
    @SerializedName("active") val active: Boolean?
)