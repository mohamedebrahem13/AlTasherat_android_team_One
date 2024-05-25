package com.solutionplus.altasherat.features.splash.data.repository

import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.local.FakeSplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.FakeNetworkProvider
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashRepositoryTest {
    private lateinit var remoteDataSource: FakeSplashRemoteDS
    private lateinit var countryMapper: CountryMapper
    private lateinit var localDataSource: FakeSplashLocalDS
    private lateinit var splashRepository: ISplashRepository
    private lateinit var networkProvider: FakeNetworkProvider


    @Before
    fun setUp() {
        localDataSource = FakeSplashLocalDS(FakeKeyValueStorageProvider())
        networkProvider = FakeNetworkProvider()
        remoteDataSource = FakeSplashRemoteDS(networkProvider)
        countryMapper = CountryMapper // Initialize the countryMapper object
        splashRepository = SplashRepository(localDataSource, remoteDataSource, CountryMapper)
    }
     @Test
     fun testSaveCountries_withValidCountryList_shouldSaveCorrectlyAndMapTheList() = runTest {
         // Given
         val countriesToSave = listOf(
             Country(
                 1,
                 "Country1",
                 "Nationality1",
                 "Currency1",
                 "Code1",
                 "PhoneCode1",
                 true,
                 "Flag1"
             ),
             Country(
                 2,
                 "Country2",
                 "Nationality2",
                 "Currency2",
                 "Code2",
                 "PhoneCode2",
                 true,
                 "Flag2"
             )
         )

         // When
         splashRepository.saveCountries(countriesToSave)

         // Then
         val savedCountries = splashRepository.getCountriesFromLocal()
         println("Saved Countries: $savedCountries") // Log the list of saved countries

         assertEquals(countriesToSave, savedCountries)
         // Add more assertions based on your requirements
     }
    @Test
    fun testGetCountriesFromRemote_withValidResponse_shouldReturnMappedCountriesResponse() = runTest {
        // Given
        val countryDtos = listOf(
            CountryDto(
                id = 1,
                name = "Country1",
                nationality = "Nationality1",
                currency = "Currency1",
                code = "Code1",
                phoneCode = "PhoneCode1",
                visible = true,
                flag = "Flag1"
            ),
            CountryDto(
                id = 2,
                name = "Country2",
                nationality = "Nationality2",
                currency = "Currency2",
                code = "Code2",
                phoneCode = "PhoneCode2",
                visible = true,
                flag = "Flag2"
            )
        )
        val countryResponseDto = CountryResponseDto(data = countryDtos,null,null)

        networkProvider.setResponse("countries", countryResponseDto)

        // When
        val result = splashRepository.getCountriesFromRemote("en")

        // Then
        val expectedCountries = CountriesResponse(
            listOf(
                Country(
                    id = 1,
                    name = "Country1",
                    nationality = "Nationality1",
                    currency = "Currency1",
                    code = "Code1",
                    phoneCode = "PhoneCode1",
                    visible = true,
                    flag = "Flag1"
                ),
                Country(
                    id = 2,
                    name = "Country2",
                    nationality = "Nationality2",
                    currency = "Currency2",
                    code = "Code2",
                    phoneCode = "PhoneCode2",
                    visible = true,
                    flag = "Flag2"
                )
            )
        )

        assertEquals(expectedCountries, result)
    }

    @Test
    fun testHasCountryStringKey_withKeyPresent_shouldReturnTrue() = runTest {
        // Given
        val countriesToSave = listOf(
            CountryEntity(
                1,
                "Country1",
                "Nationality1",
                "Currency1",
                "Code1",
                "PhoneCode1",
                true,
                "Flag1"
            ),
            CountryEntity(
                2,
                "Country2",
                "Nationality2",
                "Currency2",
                "Code2",
                "PhoneCode2",
                true,
                "Flag2"
            )
        )
        localDataSource.saveCountryString(countriesToSave)

        // When
        val result = splashRepository.hasCountryStringKey()

        // Then
        assertTrue(result)
    }
    @Test
    fun testHasCountryStringKey_withKeyNotPresent_shouldReturnFalse() = runTest {
        // Given
        // Ensure no key is present in the local data source

        // When
        val result = splashRepository.hasCountryStringKey()

        // Then
        assertFalse(result)
    }

    @Test
    fun testGetCountriesFromLocal_withValidData_shouldReturnMappedCountries() = runTest {
        // Given
        val countriesToSave = listOf(
            Country(
                1,
                "Country1",
                "Nationality1",
                "Currency1",
                "Code1",
                "PhoneCode1",
                true,
                "Flag1"
            ),
            Country(
                2,
                "Country2",
                "Nationality2",
                "Currency2",
                "Code2",
                "PhoneCode2",
                true,
                "Flag2"
            )
        )

        // When
        splashRepository.saveCountries(countriesToSave)

        // Then
        val savedCountries = splashRepository.getCountriesFromLocal()
        println("Saved Countries: $savedCountries") // Log the list of saved countries

        assertEquals(countriesToSave, savedCountries)
        // Add more assertions based on your requirements
    }

}