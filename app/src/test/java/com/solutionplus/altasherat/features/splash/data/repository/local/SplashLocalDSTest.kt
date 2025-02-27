package com.solutionplus.altasherat.features.splash.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashLocalDSTest {
    private lateinit var preferenceStorage: IKeyValueStorageProvider
    private lateinit var splashLocalDS: ISplashLocalDS

    @Before
    fun setUp() {
        preferenceStorage = FakeKeyValueStorageProvider()
        splashLocalDS = SplashLocalDS(preferenceStorage)
    }

    @Test
    fun `hasCountryStringKey without saving country entities should return false`() = runTest {
        // Verify that the hasKey function returns false when the key is not present
        val hasKey = splashLocalDS.hasCountryStringKey()
        assertFalse(hasKey)
    }

    @Test
    fun `getUserPreferredLanguage with missing key should return default value`() = runTest {

        // When
        val language = splashLocalDS.getUserPreferredLanguage()

        // Then
        assertEquals("ar", language) // Default value
    }

    @Test
    fun `getUserPreferredCountry with missing key should return default value`() = runTest {
        //  Given
        val defaultLanguage =""

        // When
        val language = splashLocalDS.getUserPreferredCountry()

        // Then
        assertEquals(defaultLanguage, language) // Default value
    }

    @Test
    fun `saveUserPreferredLanguage with valid language should save to storage`() = runTest {
        //  Given
        val language = "en"

        // When
        splashLocalDS.saveUserPreferredLanguage(language)

        // Then
        val savedLanguage = splashLocalDS.getUserPreferredLanguage()
        assertEquals(language, savedLanguage)

    }
    @Test
    fun `saveUserPreferredCountry with valid country should save to storage`() = runTest {
        //  Given
        val country = "USA"

        // When
        splashLocalDS.saveUserPreferredCountry(country)

        // Then
        val savedCountry = splashLocalDS.getUserPreferredCountry()
        assertEquals(country, savedCountry)

    }

    @Test
    fun `isOnboardingShown with missing key should return default value`() = runTest {

        // When
        val isShown = splashLocalDS.isOnboardingShown()

        // Then
        assertFalse(isShown) // Default value
    }
    @Test
    fun `setOnboardingShown with true should save true to storage`() = runTest {
        //  Given
        val shown = true

        // When
        splashLocalDS.setOnboardingShown(shown)

        // Then
        val isOnboardingShown = splashLocalDS.isOnboardingShown()
        assertTrue(isOnboardingShown)
    }

    @Test
    fun `setOnboardingShown with false should save false to storage`() = runTest {
        //  Given
        val shown = false

        // When
        splashLocalDS.setOnboardingShown(shown)

        // Then
        val isOnboardingShown = splashLocalDS.isOnboardingShown()
        assertFalse(isOnboardingShown)
    }

}