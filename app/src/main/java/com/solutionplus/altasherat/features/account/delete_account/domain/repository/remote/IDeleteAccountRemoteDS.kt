package com.solutionplus.altasherat.features.account.delete_account.domain.repository.remote

import com.solutionplus.altasherat.features.account.delete_account.data.repository.models.dto.DeleteAccountResponseDto

internal interface IDeleteAccountRemoteDS {
    suspend fun deleteAccount(password: String): DeleteAccountResponseDto
}