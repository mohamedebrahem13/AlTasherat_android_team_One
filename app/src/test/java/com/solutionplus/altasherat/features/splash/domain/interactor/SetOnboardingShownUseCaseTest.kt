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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class SetOnboardingShownUseCaseTest {
    private lateinit var splashRepository: FakeSplashRepository
    private lateinit var useCase: SetOnboardingShownUseCase
    private lateinit var localDataSource: FakeSplashLocalDS
    private lateinit var remoteDataSource: FakeSplashRemoteDS
    private lateinit var networkProvider: FakeNetworkProvider
    private lateinit var countryMapper: CountryMapper // Add CountryMapper

    @Before
    fun setUp() {
        networkProvider = FakeNetworkProvider()
        countryMapper = CountryMapper // Initialize the CountryMapper
        remoteDataSource = FakeSplashRemoteDS(networkProvider)
        localDataSource = FakeSplashLocalDS(FakeKeyValueStorageProvider())
        splashRepository = FakeSplashRepository(remoteDataSource, localDataSource,countryMapper)
        useCase = SetOnboardingShownUseCase(splashRepository)
    }

    @Test
    fun ` SetOnboardingShownUseCase with isOnboardingShown false should emit loading and update local data source`() = runTest {
        // Given
        val isOnboardingShown = false // Set onboarding not shown
        val expectedValue = localDataSource.isOnboardingShown()
        // When
        val resultFlow= useCase(isOnboardingShown)
        val resultList = resultFlow.take(1).toList()

        // Then
        assertTrue(resultList[0] is Resource.Progress)
        assertTrue((resultList[0] as Resource.Progress).loading) // Ensure loading state is true
        assertTrue(expectedValue == isOnboardingShown) // Check if the value is updated in local data source
    }
    @Test
    fun `SetOnboardingShownUseCase with isOnboardingShown false should emit success state after updating local data source`() = runTest {
        // Given
        val isOnboardingShown = false // Set onboarding not shown

        // When
        val resultFlow = useCase(isOnboardingShown)
        val resultList = resultFlow.take(2).toList()

        // Then
        assertTrue(resultList[1] is Resource.Success) // Check if the second state is success
    }
    @Test
    fun `SetOnboardingShownUseCase with isOnboardingShown false should emit loading state initially`() = runTest {
        // Given
        val isOnboardingShown = false
        
        // When
        val resultFlow = useCase(isOnboardingShown)
        val resultList = resultFlow.take(1).toList()

        // Then
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Resource.Progress) // Check if the first state is loading
        assertTrue((resultList[0] as Resource.Progress).loading) // Ensure loading state is true
    }
    @Test
    fun `SetOnboardingShownUseCase with isOnboardingShown true should emit loading and then update local data source`() = runTest {
        // Given
        val isOnboardingShown = true
        // When
        val resultFlow = useCase(isOnboardingShown)
        val resultList = resultFlow.take(2).toList()
        println("state_value: $resultFlow")
        // Retrieve the updated value after the asynchronous operation completes
        val expectedValue = splashRepository.isOnboardingShown()
        println("Expected value: $expectedValue")

        // Then
        assertTrue(resultList[1] is Resource.Success)
        assertTrue(expectedValue) // Check if the value is updated in local data source
    }
}