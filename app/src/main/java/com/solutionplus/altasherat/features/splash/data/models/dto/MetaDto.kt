package com.solutionplus.altasherat.features.splash.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class MetaDto(
    @SerializedName("current_page") val currentPage: Int?,
    @SerializedName("from") val from: Int?,
    @SerializedName("last_page") val lastPage: Int?,
    @SerializedName("links") val links: List<LinkDto>?,
    @SerializedName("path") val path: String?,
    @SerializedName("per_page") val perPage: Int?,
    @SerializedName("to") val to: Int?,
    @SerializedName("total") val total: Int?
)