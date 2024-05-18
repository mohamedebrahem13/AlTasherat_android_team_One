package com.solutionplus.altasherat.features.splash.data.repository.remote

import com.solutionplus.altasherat.features.splash.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SplashRemoteDSTest{
    private lateinit var networkProvider: FakeNetworkProvider
    private lateinit var splashRemoteDS: ISplashRemoteDS

    @Before
    fun setUp() {
        networkProvider = FakeNetworkProvider()
        splashRemoteDS = SplashRemoteDS(networkProvider)
    }

    @Test
    fun `getCountries with valid response should return expected CountryResponseDto`() = runTest {
        // Arrange
        val expectedResponse = CountryResponseDto(
            data = listOf(
                CountryDto(id = 1, name = "USA", nationality = "American", currency = "USD", code = "US", phoneCode = "+1", visible = true, flag = "us_flag.png"),
                CountryDto(id = 2, name = "UK", nationality = "British", currency = "GBP", code = "UK", phoneCode = "+44", visible = true, flag = "uk_flag.png"),
                CountryDto(id = 3, name = "Canada", nationality = "Canadian", currency = "CAD", code = "CA", phoneCode = "+1", visible = true, flag = "ca_flag.png")
            ),
            links = null,
            meta = null
        )
        networkProvider.setResponse("countries", expectedResponse)

        // Act
        val result = splashRemoteDS.getCountries()

        // Assert
        assertEquals(expectedResponse, result)
    }
}