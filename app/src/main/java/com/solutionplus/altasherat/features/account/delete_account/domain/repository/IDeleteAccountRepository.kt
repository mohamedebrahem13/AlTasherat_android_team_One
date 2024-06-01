package com.solutionplus.altasherat.features.account.delete_account.domain.repository

interface IDeleteAccountRepository {
    suspend fun deleteAccount(password: String): String
}