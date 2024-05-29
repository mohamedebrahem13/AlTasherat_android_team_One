package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.personal_info.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.data.repository.PersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.remote.IPersonalInfoRemoteDS
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
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UpdatePersonalInfoUCTest {

    private lateinit var personalInfoRemoteDS: IPersonalInfoRemoteDS
    private lateinit var personalInfoRepository: IPersonalInfoRepository
    private lateinit var updatePersonalInfoUC: UpdatePersonalInfoUC
    private lateinit var updatePersonalInfoResponse: UpdateInfoResponseDto
    private lateinit var phoneRequest: PhoneRequest
    private lateinit var updateInfoRequest: UpdateInfoRequest

    @Before
    fun setUp() {
        phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        updateInfoRequest = UpdateInfoRequest(
            firstname = "Ahmed",
            middlename = "Hassan",
            lastname = "ElSayed",
            email = "ahmed.hassan@example.com",
            birthDate = "1985-05-15",
            phone = phoneRequest,
            image = "ahmed_el_sayed_profile.png",
            countryId = 1
        )

        updatePersonalInfoResponse = mock<UpdateInfoResponseDto> {
            on { message } doReturn "Updated successfully"
        }
        personalInfoRemoteDS = mock<IPersonalInfoRemoteDS> {
            onBlocking { updatePersonalInfo(any<UpdateInfoRequest>()) } doReturn updatePersonalInfoResponse
        }
        personalInfoRepository = PersonalInfoRepository(personalInfoRemoteDS)
        updatePersonalInfoUC = UpdatePersonalInfoUC(personalInfoRepository)
    }

    // region State Emission Tests
    @Test
    fun updatePersonalInfo_UpdateInProgress_ReturnTrueLoadingResource() = runTest {
        // Given update info request
        val updateInfoRequest = updateInfoRequest

        // When update is in progress
        val state = updatePersonalInfoUC(updateInfoRequest).first()

        // Then loading resource should be emitted
        assertTrue((state as Resource.Progress).loading)
    }

    @Test
    fun updatePersonalInfo_UpdateSuccessful_ReturnSuccessResource() = runTest {
        // Given update info request
        val updateInfoRequest = updateInfoRequest

        // When update is successful
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then success resource should be emitted
        assertEquals(updatePersonalInfoResponse.message, (state as Resource.Success).model.message)
    }

    @Test
    fun updatePersonalInfo_UpdateFailed_ReturnFailureResource() = runTest {
        // Given update info request
        val updateInfoRequest = updateInfoRequest

        // When update fails
        whenever(personalInfoRemoteDS.updatePersonalInfo(updateInfoRequest)) doAnswer { throw Exception() }
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then failure resource should be emitted
        assertTrue(state is Resource.Failure)
    }

    @Test
    fun updatePersonalInfo_UpdateSuccessful_ReturnFalseLoadingResource() = runTest {
        // Given update info request
        val updateInfoRequest = updateInfoRequest

        // When update is successful
        val state = updatePersonalInfoUC(updateInfoRequest).drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }

    @Test
    fun updatePersonalInfo_UpdateFailed_ReturnFalseLoadingResource() = runTest {
        // Given update info request
        val updateInfoRequest = updateInfoRequest

        // When update fails
        whenever(personalInfoRemoteDS.updatePersonalInfo(updateInfoRequest)) doAnswer { throw Exception() }
        val state = updatePersonalInfoUC(updateInfoRequest).drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }
    // endregion

    // region FirstName Validation Tests
    @Test
    fun updatePersonalInfo_EmptyFirstName_ReturnRequestValidationFailureResource() = runTest {
        // Given update request with empty phone number
        val updateInfoRequest = updateInfoRequest.copy(firstname = "")

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun updatePersonalInfo_WhiteSpacesFirstName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with white spaces phone number
            val updateInfoRequest =
                updateInfoRequest.copy(firstname = "    ")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_FirstNameWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing white spaces
            val updateInfoRequest = updateInfoRequest.copy(firstname = "Ahmed Hassan")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphaNumericFirstName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with alpha-numeric phone number
            val updateInfoRequest =
                updateInfoRequest.copy(firstname = "Ahmed123")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_FirstNameWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing special characters
            val updateInfoRequest =
                updateInfoRequest.copy(firstname = "Ahmed!@")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_FirstNameLessThanThreeChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number less than nine digits
            val updateInfoRequest = updateInfoRequest.copy(firstname = "Ah")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_FirstNameGreaterThanFifteenChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number greater than nine digits
            val updateInfoRequest = updateInfoRequest.copy(firstname = "AhmedMohamedHassanElSayed")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_NumericalFirstName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing alphabets
            val updateInfoRequest =
                updateInfoRequest.copy(firstname = "1234567890")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region MiddleName Validation Tests
    @Test
    fun updatePersonalInfo_MiddleNameWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing white spaces
            val updateInfoRequest = updateInfoRequest.copy(middlename = "Ahmed Hassan")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphaNumericMiddleName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with alpha-numeric phone number
            val updateInfoRequest = updateInfoRequest.copy(middlename = "Ahmed123")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_MiddleNameWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing special characters
            val updateInfoRequest = updateInfoRequest.copy(middlename = "Ahmed!@")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_MiddleNameGreaterThanFifteenChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number greater than nine digits
            val updateInfoRequest = updateInfoRequest.copy(middlename = "AhmedMohamedHassanElSayed")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_NumericalMiddleName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing alphabets
            val updateInfoRequest = updateInfoRequest.copy(middlename = "1234567890")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region LastName Validation Tests
    @Test
    fun updatePersonalInfo_EmptyLastName_ReturnRequestValidationFailureResource() = runTest {
        // Given update request with empty phone number
        val updateInfoRequest = updateInfoRequest.copy(lastname = "")

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun updatePersonalInfo_WhiteSpacesLastName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with white spaces phone number
            val updateInfoRequest =
                updateInfoRequest.copy(lastname = "    ")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_LastNameWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing white spaces
            val updateInfoRequest = updateInfoRequest.copy(lastname = "Ahmed Hassan")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphaNumericLastName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with alpha-numeric phone number
            val updateInfoRequest = updateInfoRequest.copy(lastname = "Ahmed123")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_LastNameWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing special characters
            val updateInfoRequest = updateInfoRequest.copy(lastname = "Ahmed!@")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_LastNameLessThanThreeChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number less than nine digits
            val updateInfoRequest = updateInfoRequest.copy(lastname = "Ah")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_LastNameGreaterThanFifteenChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number greater than nine digits
            val updateInfoRequest = updateInfoRequest.copy(lastname = "AhmedMohamedHassanElSayed")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_NumericalLastName_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing alphabets
            val updateInfoRequest = updateInfoRequest.copy(lastname = "1234567890")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region Email Validation Tests
    @Test
    fun updatePersonalInfo_EmptyEmail_ReturnRequestValidationFailureResource() = runTest {
        // Given update request with empty phone number
        val updateInfoRequest = updateInfoRequest.copy(email = "")

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun updatePersonalInfo_WhiteSpacesEmail_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with white spaces phone number
            val updateInfoRequest = updateInfoRequest.copy(email = "    ")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_EmailWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing white spaces
            val updateInfoRequest = updateInfoRequest.copy(email = "ahmed @gmail.com")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_EmailWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing special characters
            val updateInfoRequest = updateInfoRequest.copy(email = "ahmed!@@gmail.com")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_EmailGreaterThanFiftyChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number greater than nine digits
            val updateInfoRequest =
                updateInfoRequest.copy(email = "ahmedmohamedelsayedalihasanahmedmohamedfff@gmail.com")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region BirthDate Validation Tests
    @Test
    fun updatePersonalInfo_BirthDateWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing white spaces
            val updateInfoRequest = updateInfoRequest.copy(birthDate = "1985 - 05 - 15")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphaNumericBirthDate_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with alpha-numeric phone number
            val updateInfoRequest =
                updateInfoRequest.copy(birthDate = "1985-05-15aA")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_BirthDateWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing special characters
            val updateInfoRequest =
                updateInfoRequest.copy(birthDate = "1985-05-15!@")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_BirthDateRequiredFormat_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing alphabets
            val updateInfoRequest =
                updateInfoRequest.copy(birthDate = "15-05-1985")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_FutureBirthDate_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing alphabets
            val updateInfoRequest =
                updateInfoRequest.copy(birthDate = "2100-05-15")

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region Country Code Validation Tests
    @Test
    fun updatePersonalInfo_EmptyCountryCode_ReturnRequestValidationFailureResource() = runTest {
        // Given update info request with empty country code
        val updateInfoRequest = updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = ""))

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun updatePersonalInfo_WhiteSpacesCountryCode_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update info request with country code containing white spaces
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "    "))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_CountryCodeWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update info request with country code containing white spaces
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "00 20"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_CountryCodeWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update info request with country code containing special characters
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "00!@"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphaNumericCountryCode_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with country code containing alphabets
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "00aA"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_CountryCodeLessThanThreeDigits_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with country code less than four digits
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "20"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_CountryCodeGreaterThanFiveDigits_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with country code greater than four digits
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "002002"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphabeticalCountryCode_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with country code containing alphabets
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(countryCode = "abcdg"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region Phone Number Validation Tests
    @Test
    fun updatePersonalInfo_EmptyPhoneNumber_ReturnRequestValidationFailureResource() = runTest {
        // Given update request with empty phone number
        val updateInfoRequest = updateInfoRequest.copy(phone = phoneRequest.copy(number = ""))

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun updatePersonalInfo_WhiteSpacesPhoneNumber_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with white spaces phone number
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "    "))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_PhoneNumberWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing white spaces
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "100 100 100"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphaNumericPhoneNumber_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with alpha-numeric phone number
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "1001an0010"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_PhoneNumberWithSpecialCharacters_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing special characters
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "1001!@100"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_PhoneNumberLessThanNineDigits_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number less than nine digits
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "10010010"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_PhoneNumberGreaterThanFifteenDigits_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number greater than nine digits
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "1001001001001002"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun updatePersonalInfo_AlphabeticalPhoneNumber_ReturnRequestValidationFailureResource() =
        runTest {
            // Given update request with phone number containing alphabets
            val updateInfoRequest =
                updateInfoRequest.copy(phone = phoneRequest.copy(number = "abcdefghi"))

            // When update fails
            val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region Image Validation Tests
    // endregion

    // region CountryId Validation Tests
    @Test
    fun updatePersonalInfo_NegativeCountryId_ReturnRequestValidationFailureResource() = runTest {
        // Given update request with empty phone number
        val updateInfoRequest = updateInfoRequest.copy(countryId = -1)

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun updatePersonalInfo_ZeroCountryId_ReturnRequestValidationFailureResource() = runTest {
        // Given update request with empty phone number
        val updateInfoRequest = updateInfoRequest.copy(countryId = 0)

        // When update fails
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }
    // endregion
}