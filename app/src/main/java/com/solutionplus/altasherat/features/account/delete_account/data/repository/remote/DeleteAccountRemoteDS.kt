package com.solutionplus.altasherat.features.account.delete_account.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.account.delete_account.data.repository.models.dto.DeleteAccountResponseDto
import com.solutionplus.altasherat.features.account.delete_account.domain.repository.remote.IDeleteAccountRemoteDS

internal class DeleteAccountRemoteDS(
    private val networkProvider: INetworkProvider
) : IDeleteAccountRemoteDS {

    override suspend fun deleteAccount(password: String): DeleteAccountResponseDto {
        return networkProvider.post(
            pathUrl = "delete-account",
            headers = hashMapOf("Authorization-Required" to "true"),
            requestBody = hashMapOf("password" to password),
            responseWrappedModel = DeleteAccountResponseDto::class.java
        )
    }
}