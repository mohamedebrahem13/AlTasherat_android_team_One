package com.solutionplus.altasherat.features.splash.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashLocalDSTest{
    private lateinit var preferenceStorage: IKeyValueStorageProvider
    private lateinit var splashLocalDS: ISplashLocalDS
    @Before
    fun setUp() {
        preferenceStorage = FakeKeyValueStorageProvider()
        splashLocalDS = SplashLocalDS(preferenceStorage)
    }
    @Test
    fun `saveCountryString should save the list of country entities and check if the return countries is the same`() = runTest {
        val countries = listOf(
            CountryEntity(1, "USA", "American", "USD", "US", "+1", true, "us_flag.png"),
            CountryEntity(2, "UK", "British", "GBP", "UK", "+44", true, "uk_flag.png"),
            CountryEntity(3, "Canada", "Canadian", "CAD", "CA", "+1", true, "ca_flag.png")
        )
        val expectedJson = "[{\"id\":1,\"name\":\"USA\",\"nationality\":\"American\",\"currency\":\"USD\",\"code\":\"US\",\"phoneCode\":\"+1\",\"visible\":true,\"flag\":\"us_flag.png\"},{\"id\":2,\"name\":\"UK\",\"nationality\":\"British\",\"currency\":\"GBP\",\"code\":\"UK\",\"phoneCode\":\"+44\",\"visible\":true,\"flag\":\"uk_flag.png\"},{\"id\":3,\"name\":\"Canada\",\"nationality\":\"Canadian\",\"currency\":\"CAD\",\"code\":\"CA\",\"phoneCode\":\"+1\",\"visible\":true,\"flag\":\"ca_flag.png\"}]"

        // Call the method under test
        splashLocalDS.saveCountryString(countries)

        // Verify that the save function was called with the correct arguments
      val countriesJson=  preferenceStorage.get(
            StorageKeyEnum.COUNTRIES_STRING,
          "",
            String::class.java
        )
        assertEquals(countriesJson, expectedJson)

    }
    @Test
    fun `hasCountryStringKey should return true if the country string key exists`() = runTest {
        // Mock data
        val countries = listOf(
            CountryEntity(1, "USA", "American", "USD", "US", "+1", true, "us_flag.png")
        )

        // Save the country string to the preference storage
        splashLocalDS.saveCountryString(countries)

        // Verify that the hasKey function returns true
        val hasKey = splashLocalDS.hasCountryStringKey()
        assertTrue(hasKey)
    }

    @Test
    fun `hasCountryStringKey should return false if the country string key does not exist`() = runTest {
        // Verify that the hasKey function returns false when the key is not present
        val hasKey = splashLocalDS.hasCountryStringKey()
        assertFalse(hasKey)
    }
}