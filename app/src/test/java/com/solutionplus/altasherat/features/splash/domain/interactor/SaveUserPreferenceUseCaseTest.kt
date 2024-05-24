package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.repository.FakeSplashRemoteDS
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.FakeNetworkProvider
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SaveUserPreferenceUseCaseTest{
    private lateinit var splashRepository: FakeSplashRepository
    private lateinit var useCase: SaveUserPreferenceUseCase
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
        useCase = SaveUserPreferenceUseCase(splashRepository)
    }
    @Test
    fun `SaveUserPreferenceUseCase with UserPreference should update local data source`() = runTest {
        // Given
        val userPreference = UserPreference(preferredLanguage = "ar", preferredCountry = "USA")
        // When
          useCase(userPreference).take(2).toList()
        // Then
        assertEquals("ar", splashRepository.getUserPreferredLanguage())
        assertEquals("USA", splashRepository.getUserPreferredCountry())
    }
    @Test
    fun `SaveUserPreferenceUseCase with UserPreference should emit Success and then update local data source`() = runTest {
        // Given
        val userPreference = UserPreference(preferredLanguage = "ar", preferredCountry = "USA")

        // When
        val resultFlow = useCase(userPreference)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size) // Two states: loading, success
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue(resultList[1] is Resource.Success)

        // Verify that local data source is updated
        assertEquals("ar", splashRepository.getUserPreferredLanguage())
        assertEquals("USA", splashRepository.getUserPreferredCountry())
    }
    @Test
    fun `SaveUserPreferenceUseCase with UserPreference should emit Loading and then update local data source`() = runTest {
        // Given
        val userPreference = UserPreference(preferredLanguage = "ar", preferredCountry = "USA")

        // When
        val resultFlow = useCase(userPreference)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertEquals(2, resultList.size) // Two states: loading, success
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
        assertTrue(resultList[1] is Resource.Success)

        // Verify that local data source is updated
        assertEquals("ar", splashRepository.getUserPreferredLanguage())
        assertEquals("USA", splashRepository.getUserPreferredCountry())
    }

}