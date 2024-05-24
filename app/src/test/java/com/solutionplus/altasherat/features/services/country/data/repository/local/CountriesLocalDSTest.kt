package com.solutionplus.altasherat.features.services.country.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CountriesLocalDSTest {

    private lateinit var keyValueStorageProvider: IKeyValueStorageProvider
    private lateinit var countriesLocalDS: ICountriesLocalDS
    private lateinit var countriesJsonString: String
    private lateinit var countries: List<CountryEntity>

    @Before
    fun setUp() {
        countriesJsonString =
            "[{\"id\":1,\"name\":\"Egypt\",\"currency\":\"EGP\",\"code\":\"EG\",\"phoneCode\":\"0020\",\"flag\":\"\uD83C\uDDF8\uD83C\uDDE6\"},{\"id\":2,\"name\":\"Saudi Arabia\",\"currency\":\"SAR\",\"code\":\"SA\",\"phoneCode\":\"00966\",\"flag\":\"\uD83C\uDDF8\uD83C\uDDE6\"}]"

        countries = listOf(
            CountryEntity(
                id = 1, name = "Egypt", currency = "EGP", code = "EG",
                phoneCode = "0020", flag = "\uD83C\uDDF8\uD83C\uDDE6",
            ),
            CountryEntity(
                id = 2, name = "Saudi Arabia", currency = "SAR", code = "SA",
                phoneCode = "00966", flag = "\uD83C\uDDF8\uD83C\uDDE6",
            ),
        )

        keyValueStorageProvider = mock {
            onBlocking {
                get(any<IStorageKeyEnum>(), any<String>(), any())
            } doReturn countriesJsonString
        }

        countriesLocalDS = CountriesLocalDS(keyValueStorageProvider)
    }

    @Test
    fun getCountries_GetCountriesSuccessful_ReturnCountries() = runTest {
        // Given
        // When get countries is called
        val result = countriesLocalDS.getCountries()

        // Then countries should be emitted
        assertEquals(countries, result)
    }

    @Test
    fun getCachedCountries_GetCountriesFailed_ReturnFailureResource() = runTest {
        // Given
        // When get countries is called
        whenever(
            keyValueStorageProvider.get(any<IStorageKeyEnum>(), any<String>(), any())
        ) doReturn ""
        val result = countriesLocalDS.getCountries()

        // Then list should be empty
        assertTrue(result.isEmpty())
    }
}