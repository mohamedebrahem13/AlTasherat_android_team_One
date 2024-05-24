package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.splash.data.repository.FakeSplashRemoteDS
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.FakeNetworkProvider
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HasCountryStringKeyUseCaseTest{
    private lateinit var splashRepository: FakeSplashRepository
    private lateinit var useCase: HasCountryStringKeyUseCase
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
        useCase = HasCountryStringKeyUseCase(splashRepository)
    }
    @Test
    fun `execute hasCountryStringKey when country string key exists in the repository should emit true`() = runTest {
        // Given
        val countries = listOf(
            CountryEntity(1, "USA", "American", "USD", "US", "+1", true, "us_flag.png")
        )

        // Save the country string to the preference storage
        localDataSource.saveCountryString(countries)


        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue((resultList[1] as Resource.Success<*>).model == true) // Check if the returned value is true
    }
    @Test
    fun `execute hasCountryStringKey when no country string key exists in the repository should emit false`() = runTest {
        // Given no country data saved in the repository

        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertFalse((resultList[1] as Resource.Success<*>).model == true) // Check if the returned value is false
    }
    @Test
    fun `execute hasCountryStringKey should emit loading state when checking for country string key`() = runTest {
        // Given

        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(1).toList()

        // Then
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
    }
    @Test
    fun `execute hasCountryStringKey when an exception occurs in the repository should emit failure state `() = runTest {
        // Given
        splashRepository.setShouldThrowException(true)

        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue(resultList[1] is Resource.Failure)
    }

}