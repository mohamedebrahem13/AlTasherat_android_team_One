package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.repository.FakeSplashRemoteDS
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.FakeNetworkProvider
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IsOnboardingShownUseCaseTest{
    private lateinit var splashRepository: FakeSplashRepository
    private lateinit var useCase: IsOnboardingShownUseCase
    private lateinit var localDataSource: FakeSplashLocalDS
    private lateinit var remoteDataSource: FakeSplashRemoteDS
    private lateinit var networkProvider: FakeNetworkProvider
    private lateinit var countryMapper: CountryMapper // Add CountryMapper

    @Before
    fun setUp() {
        networkProvider = FakeNetworkProvider()
        remoteDataSource = FakeSplashRemoteDS(networkProvider)
        localDataSource = FakeSplashLocalDS(FakeKeyValueStorageProvider())
        countryMapper = CountryMapper // Initialize the CountryMapper
        splashRepository = FakeSplashRepository(remoteDataSource,localDataSource,countryMapper)
        useCase = IsOnboardingShownUseCase(splashRepository)
    }
    @Test
    fun `execute IsOnboardingShownUseCase should emit loading state when checking for onboarding string key`() = runTest {
        // Arrange
        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(1).toList()

        // Then
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading)
    }
    @Test
    fun `execute IsOnboardingShownUseCase should return true when onboarding is shown`() = runTest {

        localDataSource.setOnboardingShown(true)
        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(2).toList()
        // Then
        assertTrue((resultList[1] as Resource.Success<*>).model == true) // Check if the returned value is true
    }
    @Test
    fun `execute IsOnboardingShownUseCase should emit loading and then success states with correct result`() = runTest {
        // Given
        localDataSource.setOnboardingShown(false) // Set onboarding not shown

        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertTrue(resultList[0] is Resource.Progress) // Check if the first state is loading
        val successResult = resultList[1] as Resource.Success<*>
        assertTrue(successResult.model == false) // Check if the result is as expected
    }
    @Test
    fun `execute IsOnboardingShownUseCase should emit loading and then success states with same value after saving`() = runTest {
        // Given
        localDataSource.setOnboardingShown(false) // Set onboarding not shown
        val expectedValue = localDataSource.isOnboardingShown()

        // When
        val resultFlow = useCase(Unit) // Second call to trigger get
        val resultList = resultFlow.take(2).toList()

        // Then
        assertTrue(resultList[0] is Resource.Progress) // Check if the first state is loading
        val successResult = resultList[1] as Resource.Success<*>
        assertTrue(successResult.model == expectedValue) // Check if the result is as expected
    }


}