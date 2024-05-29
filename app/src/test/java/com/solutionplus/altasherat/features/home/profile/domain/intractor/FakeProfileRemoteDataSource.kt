package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.features.home.profile.data.models.LogoutResponse
import com.solutionplus.altasherat.features.home.profile.domain.repository.remote.IProfileRemoteDataSource


internal class FakeProfileRemoteDataSource : IProfileRemoteDataSource {

    // Fake implementation of logout function
    var shouldReturnNullMessage: Boolean = false

    override suspend fun logout(): LogoutResponse {
        // Simulate successful logout response with a null message if shouldReturnNullMessage is true
        if (shouldReturnNullMessage) {
            return LogoutResponse(message = null)
        }
        // Otherwise, return a normal successful response
        return LogoutResponse(message = "Logout successful")
    }
}