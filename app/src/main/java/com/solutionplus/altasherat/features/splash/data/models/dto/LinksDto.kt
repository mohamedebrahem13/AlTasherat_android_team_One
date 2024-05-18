package com.solutionplus.altasherat.features.splash.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class LinksDto(
    @SerializedName("first") val first: String?,
    @SerializedName("last") val last: String?,
    @SerializedName("prev") val prev: String?,
    @SerializedName("next") val next: String?
)