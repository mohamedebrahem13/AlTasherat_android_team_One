package com.solutionplus.altasherat.features.splash.data.repository
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashRepositoryTest {
    private lateinit var localDataSource: FakeSplashLocalDS
    private lateinit var splashRepository: ISplashRepository


    @Before
    fun setUp() {
        localDataSource = FakeSplashLocalDS(FakeKeyValueStorageProvider())
        splashRepository = SplashRepository(localDataSource)
    }

    @Test
    fun test_getUserPreferredCountry_withNoPreference_Should_return_default_value() = runTest {
        // Given
        // No need for additional setup, no preference is saved by default

        // When
        val country = splashRepository.getUserPreferredCountry()

        // Then
        assertEquals("السعودية", country) // Assert the country is empty string (or your default value)
    }
    @Test
    fun test_getUserPreferredLanguage_withNoPreference_Should_return_default_value() = runTest {
        // Given
        // No need for additional setup, no preference is saved by default

        // When
        val country = splashRepository.getUserPreferredLanguage()

        // Then
        assertEquals("ar", country) // Assert the country is empty string (or your default value)
    }
    @Test
    fun testSaveUserPreferredCountry_Should_return_expected_Country() = runTest {
        // Given
        val expectedCountry = "EG"

        // When
        splashRepository.saveUserPreferredCountry(expectedCountry)

        // Then
        val savedCountry = localDataSource.getUserPreferredCountry() // Access directly for testing
        assertEquals(expectedCountry, savedCountry)
    }
    @Test
    fun test_saveUserPreferredLanguage_Should_return_expected_Language() = runTest {
        // Given
        val expectedLanguage = "en"

        // When
        splashRepository.saveUserPreferredLanguage(expectedLanguage)

        // Then
        val savedCountry = localDataSource.getUserPreferredLanguage() // Access directly for testing
        assertEquals(expectedLanguage, savedCountry)
    }
    @Test
    fun test_SetOnboardingShown_With_True_Should_Set_Onboarding_True() = runTest {
        // Given
        val shown = true

        // When
        splashRepository.setOnboardingShown(shown)

        // Then
        val isOnboardingShown = localDataSource.isOnboardingShown() // Access directly for testing
        assertTrue(isOnboardingShown)
    }


}