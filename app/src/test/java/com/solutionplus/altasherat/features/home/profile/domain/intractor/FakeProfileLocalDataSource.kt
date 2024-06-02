package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.features.home.profile.domain.repository.local.IProfileLocalDataSource

internal class FakeProfileLocalDataSource : IProfileLocalDataSource {

    // Fake implementation of deleting user info
    override suspend fun deleteUserInfo() {
        // You can simulate the deletion process here
        println("User info deleted")
    }

    // Fake implementation of deleting user token
    override suspend fun deleteUserToken() {
        // You can simulate the deletion process here
        println("User token deleted")
    }

    override suspend fun hasTokenKey(): Boolean {
        return true
    }
}