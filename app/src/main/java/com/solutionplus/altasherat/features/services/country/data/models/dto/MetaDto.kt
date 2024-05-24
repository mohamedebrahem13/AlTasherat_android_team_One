package com.solutionplus.altasherat.features.services.country.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class MetaDto(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    @SerializedName("links")
    val links: List<LinkDto>? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("to")
    val to: Int? = null,
    @SerializedName("total")
    val total: Int? = null
)