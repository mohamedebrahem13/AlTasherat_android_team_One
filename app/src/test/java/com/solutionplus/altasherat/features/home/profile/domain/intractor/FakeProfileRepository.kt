package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository
import com.solutionplus.altasherat.features.home.profile.domain.repository.local.IProfileLocalDataSource
import com.solutionplus.altasherat.features.home.profile.domain.repository.remote.IProfileRemoteDataSource

internal class FakeProfileRepository(
    private val fakeRemoteDataSource: IProfileRemoteDataSource,
    private val fakeLocalDataSource: IProfileLocalDataSource
) : IProfileRepository {
    var shouldThrowException: Boolean = false

    override suspend fun logout(): String {
        // If shouldThrowException is true, throw an exception
        if (shouldThrowException) {
            throw AlTasheratException.Unknown("Logout operation failed")
        }
        // Otherwise, simulate successful logout and return a message
        return fakeRemoteDataSource.logout().message?: "Logout successful"
    }

    override suspend fun deleteUserInfo() {
        // If shouldThrowException is true, throw an exception
        if (shouldThrowException) {
            throw AlTasheratException.Unknown("delete user info operation failed")
        }
        fakeLocalDataSource.deleteUserInfo()
    }

    override suspend fun deleteUserToken() {
        // If shouldThrowException is true, throw an exception
        if (shouldThrowException) {
            throw AlTasheratException.Unknown("delete user token operation failed")
        }
        fakeLocalDataSource.deleteUserToken()
    }
}