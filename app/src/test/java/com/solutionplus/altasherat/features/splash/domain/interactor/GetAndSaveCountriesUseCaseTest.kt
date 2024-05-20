package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.data.repository.FakeSplashRemoteDS
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.FakeNetworkProvider
import com.solutionplus.altasherat.features.splash.domain.models.Country
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetAndSaveCountriesUseCaseTest{
    private lateinit var splashRepository: FakeSplashRepository
    private lateinit var useCase: GetAndSaveCountriesUseCase
    private lateinit var remoteDataSource: FakeSplashRemoteDS
    private lateinit var localDataSource: FakeSplashLocalDS
    private lateinit var networkProvider: FakeNetworkProvider
    private lateinit var countryMapper: CountryMapper // Add CountryMapper



    @Before
    fun setUp() {
        networkProvider = FakeNetworkProvider()
        remoteDataSource = FakeSplashRemoteDS(networkProvider)
        localDataSource = FakeSplashLocalDS(FakeKeyValueStorageProvider())
        countryMapper = CountryMapper // Initialize the CountryMapper
        splashRepository = FakeSplashRepository(remoteDataSource,localDataSource,countryMapper)
        useCase = GetAndSaveCountriesUseCase(splashRepository)
    }
    @Test
    fun `getAndSaveCountries with valid country list should get countries from remote and save them`() = runTest {
        // Given
        val countriesToFetch = listOf(
            Country(
                1,
                "USA",
                "American",
                "USD",
                "US",
                "+1",
                true,
                "us_flag.png"
            ),
            Country(
                2,
                "UK",
                "British",
                "GBP",
                "UK",
                "+44",
                true,
                "uk_flag.png"
            ),
            Country(
                3,
                "Canada",
                "Canadian",
                "CAD",
                "CA",
                "+1",
                true,
                "ca_flag.png"
            )
        )
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
        // When
        val resultFlow =useCase("en")
        val resultList = resultFlow.take(3).toList()

        // Then
        assertEquals(3, resultList.size) // Three states: loading, success, loading(false)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue(resultList[1] is Resource.Success)
        assertFalse((resultList[2] as Resource.Progress).loading)

        // Verify that countries are saved locally
        val savedCountries = splashRepository.getCountriesFromLocal()
        assertEquals(countriesToFetch, savedCountries)
    }
    @Test
    fun `getAndSaveCountries should emit loading state`() = runTest {
        // Given
        networkProvider.setResponse("countries", CountryResponseDto(emptyList(), null, null))

        // When
        val resultFlow = useCase("en")
        val resultList = resultFlow.take(1).toList()

        // Then
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
    }
    @Test
    fun `getAndSaveCountries should emit success state`() = runTest {
        // Given

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

        // When
        val resultFlow = useCase("en")
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue(resultList[1] is Resource.Success)
    }
    @Test
    fun `getAndSaveCountries should emit failure state when an exception occurs in the repository`() = runTest {
        // Given
        val exceptionMessage = "Error fetching countries"
        val expectedExceptionPrefix = "Unknown error in com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase@386bab88: $exceptionMessage"
        splashRepository.setShouldThrowException(true)

        // When
        val resultFlow = useCase("en")
            .catch { throwable ->
                // Ensure that the caught exception is the expected one
                assertTrue(throwable is AlTasheratException.Unknown)
                assertTrue(throwable.message!!.startsWith(expectedExceptionPrefix))
            }
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue(resultList[1] is Resource.Failure)

    }


}