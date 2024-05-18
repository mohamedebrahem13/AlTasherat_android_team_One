package com.solutionplus.altasherat.features.personal_info.domain.models

data class Image(
    val id: Int,
    val path: String,
) {
    constructor() : this(id = 0, path = "")
}