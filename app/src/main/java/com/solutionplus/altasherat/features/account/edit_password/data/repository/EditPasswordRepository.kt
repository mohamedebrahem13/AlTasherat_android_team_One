package com.solutionplus.altasherat.features.account.edit_password.data.repository

import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.IEditPasswordRepository
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.remote.IEditPasswordRemoteDS

internal class EditPasswordRepository(
    private val remoteDS: IEditPasswordRemoteDS
) : IEditPasswordRepository {

    override suspend fun editPassword(request: EditPasswordRequest): String {
        return remoteDS.editPassword(request).message.orEmpty()
    }
}