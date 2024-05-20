package com.solutionplus.altasherat.features.personal_info.data.models.entity

import com.google.gson.annotations.SerializedName

internal data class ImageEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("main")
    val isMain: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)