package com.solutionplus.altasherat.features.account.delete_account.data.repository

import com.solutionplus.altasherat.features.account.delete_account.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.account.delete_account.domain.repository.remote.IDeleteAccountRemoteDS

internal class DeleteAccountRepository(
    private val remoteDS: IDeleteAccountRemoteDS
) : IDeleteAccountRepository {

    override suspend fun deleteAccount(password: String): String {
        return remoteDS.deleteAccount(password).message.orEmpty()
    }
}