package com.solutionplus.altasherat.features.services.user.data.models.entity

internal data class ImageEntity(
    val id: Int,
    val type: String,
    val path: String,
    val title: String,
    val description: String,
    val priority: Int,
    val isMain: Boolean,
    val createdAt: String,
    val updatedAt: String
)