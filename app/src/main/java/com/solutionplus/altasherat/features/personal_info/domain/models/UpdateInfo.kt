package com.solutionplus.altasherat.features.personal_info.domain.models

import com.solutionplus.altasherat.features.services.user.domain.models.User

data class UpdateInfo(
    val message: String,
    val user: User
)
