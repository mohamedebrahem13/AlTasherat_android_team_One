package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference
import kotlinx.coroutines.flow.catch
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
    private lateinit var localDataSource: FakeSplashLocalDS
    @Before
    fun setUp() {
        localDataSource = FakeSplashLocalDS(FakeKeyValueStorageProvider())
        splashRepository = FakeSplashRepository(localDataSource)
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
    @Test
    fun `SaveUserPreferenceUseCase should emit failure state when an exception occurs saveUserPreferredCountry in the repository`() = runTest {
        // Given
        val exceptionMessage = "Error saving preferred country"
        val expectedExceptionPrefix = "Unknown error in com.solutionplus.altasherat.features.splash.domain.interactor.SaveUserPreferenceUseCase: $exceptionMessage"
        splashRepository.setShouldThrowException(true)

        // When
        val resultFlow = useCase(UserPreference(preferredLanguage = "ar", preferredCountry = "USA"))
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
    @Test
    fun `SaveUserPreferenceUseCase should emit failure state when an exception occurs saveUserPreferredLanguage in the repository`() = runTest {
        // Given
        val exceptionMessage = "Error saving preferred language"
        val expectedExceptionPrefix = "Unknown error in com.solutionplus.altasherat.features.splash.domain.interactor.SaveUserPreferenceUseCase: $exceptionMessage"
        splashRepository.setShouldThrowException(true)

        // When
        val resultFlow = useCase(UserPreference(preferredLanguage = "ar", preferredCountry = "USA"))
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