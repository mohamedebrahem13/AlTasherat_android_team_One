package com.solutionplus.altasherat.features.services.user.domain.models

data class Image(
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