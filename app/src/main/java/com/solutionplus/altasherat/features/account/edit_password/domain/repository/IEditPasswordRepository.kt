package com.solutionplus.altasherat.features.account.edit_password.domain.repository

import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest

interface IEditPasswordRepository {
    suspend fun editPassword(request: EditPasswordRequest): String
}