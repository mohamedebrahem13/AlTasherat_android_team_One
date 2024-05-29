package com.solutionplus.altasherat.features.home.profile.data.repository.remote

import com.solutionplus.altasherat.features.home.profile.data.models.LogoutResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProfileRemoteDataSourceTest{

    private lateinit var dataSource: ProfileRemoteDataSource
    private lateinit var networkProvider: FakeNetworkProvider
    @Before
    fun setUp() {
        networkProvider = FakeNetworkProvider()
        dataSource = ProfileRemoteDataSource(networkProvider)
    }
    @Test
    fun `execute logout should return expected response`()= runTest {
        // Given
        val expectedResponse = LogoutResponse("Logout successful")

        // When
        val actualResponse =  dataSource.logout()

        // Then
        assertEquals(expectedResponse, actualResponse)
    }
}