package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.data.repository.CountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetCachedCountriesUCTest {

    private lateinit var countriesRemoteDS: ICountriesRemoteDS
    private lateinit var countriesLocalDS: ICountriesLocalDS
    private lateinit var countriesRepository: ICountriesRepository
    private lateinit var getCachedCountriesUC: GetCachedCountriesUC
    private lateinit var countriesLocal: List<CountryEntity>
    private lateinit var countries: List<Country>

    @Before
    fun setUp() {
        countriesLocal = listOf(
            CountryEntity(
                id = 1, name = "Egypt", currency = "EGP", code = "EG",
                phoneCode = "0020", flag = "\uD83C\uDDF8\uD83C\uDDE6",
            ),
            CountryEntity(
                id = 2, name = "Saudi Arabia", currency = "SAR", code = "SA",
                phoneCode = "00966", flag = "\uD83C\uDDF8\uD83C\uDDE6",
            ),
            CountryEntity(
                id = 3, name = "United Arab Emirates", currency = "AED", code = "AE",
                phoneCode = "00971", flag = "\uD83C\uDDF8\uD83C\uDDE6",
            )
        )

        countries = listOf(
            Country(
                id = 1, name = "Egypt", currency = "EGP", code = "EG",
                phoneCode = "0020", flag = "\uD83C\uDDF8\uD83C\uDDE6", isSelected = false
            ),
            Country(
                id = 2, name = "Saudi Arabia", currency = "SAR", code = "SA",
                phoneCode = "00966", flag = "\uD83C\uDDF8\uD83C\uDDE6", isSelected = false
            ),
            Country(
                id = 3, name = "United Arab Emirates", currency = "AED", code = "AE",
                phoneCode = "00971", flag = "\uD83C\uDDF8\uD83C\uDDE6", isSelected = false
            )
        )

        countriesRemoteDS = mock()
        countriesLocalDS = mock<ICountriesLocalDS> {
            onBlocking { getCountries() } doReturn countriesLocal
        }
        countriesRepository = CountriesRepository(countriesRemoteDS, countriesLocalDS)
        getCachedCountriesUC = GetCachedCountriesUC(countriesRepository)
    }

    @Test
    fun getCachedCountries_GetCountriesInProgress_ReturnTrueLoadingResource() = runTest {
        // Given
        // When get cached countries is called
        val state = getCachedCountriesUC().first()

        // Then loading resource should be emitted
        assertTrue((state as Resource.Progress).loading)
    }

    @Test
    fun getCachedCountries_GetCountriesSuccessful_ReturnSuccessResource() = runTest {
        // Given
        // When get cached countries is called
        val state = getCachedCountriesUC().drop(1).first()

        // Then success resource should be emitted
        assertEquals(countries, (state as Resource.Success).model)
    }

    @Test
    fun getCachedCountries_GetCountriesFailed_ReturnFailureResource() = runTest {
        // Given
        // When get cached countries fails
        whenever(countriesRepository.getCountriesFromLocal()) doAnswer { throw Exception() }
        val state = getCachedCountriesUC().drop(1).first()

        // Then failure resource should be emitted
        assertTrue(state is Resource.Failure)
    }

    @Test
    fun getCachedCountries_GetCountriesSuccessful_ReturnFalseLoadingResource() = runTest {
        // Given
        // When get cached countries is called
        val state = getCachedCountriesUC().drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }

    @Test
    fun getCachedCountries_GetCountriesFailed_ReturnFalseLoadingResource() = runTest {
        // Given
        // When get cached countries fails
        whenever(countriesRepository.getCountriesFromLocal()) doAnswer { throw Exception() }
        val state = getCachedCountriesUC().drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }
}