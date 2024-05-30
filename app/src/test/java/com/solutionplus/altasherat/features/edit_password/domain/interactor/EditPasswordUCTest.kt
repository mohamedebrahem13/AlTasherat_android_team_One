package com.solutionplus.altasherat.features.edit_password.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.edit_password.data.models.dto.EditPasswordResponseDto
import com.solutionplus.altasherat.features.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.edit_password.data.repository.EditPasswordRepository
import com.solutionplus.altasherat.features.edit_password.domain.repository.IEditPasswordRepository
import com.solutionplus.altasherat.features.edit_password.domain.repository.remote.IEditPasswordRemoteDS
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
class EditPasswordUCTest {

    private lateinit var editPasswordRemoteDS: IEditPasswordRemoteDS
    private lateinit var editPasswordRepository: IEditPasswordRepository
    private lateinit var editPasswordUC: EditPasswordUC
    private lateinit var editPasswordResponse: EditPasswordResponseDto
    private lateinit var editPasswordRequest: EditPasswordRequest

    @Before
    fun setUp() {
        editPasswordRequest = EditPasswordRequest(
            oldPassword = "123456789",
            newPassword = "12345678922",
            confirmPassword = "12345678922"
        )

        editPasswordResponse = mock<EditPasswordResponseDto> {
            on { message } doReturn "Updated successfully"
        }
        editPasswordRemoteDS = mock<IEditPasswordRemoteDS> {
            onBlocking { editPassword(any<EditPasswordRequest>()) } doReturn editPasswordResponse
        }
        editPasswordRepository = EditPasswordRepository(editPasswordRemoteDS)
        editPasswordUC = EditPasswordUC(editPasswordRepository)
    }

    // region State Emission Tests
    @Test
    fun editPassword_EditInProgress_ReturnTrueLoadingResource() = runTest {
        // Given edit password request
        val editPasswordRequest = editPasswordRequest

        // When edit is in progress
        val state = editPasswordUC(editPasswordRequest).first()

        // Then loading resource should be emitted
        assertTrue((state as Resource.Progress).loading)
    }

    @Test
    fun editPassword_EditSuccessful_ReturnSuccessResource() = runTest {
        // Given edit password request
        val editPasswordRequest = editPasswordRequest

        // When edit is successful
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then success resource should be emitted
        assertTrue(state is Resource.Success)
    }

    @Test
    fun editPassword_EditFailed_ReturnFailureResource() = runTest {
        // Given edit password request
        val editPasswordRequest = editPasswordRequest

        // When edit fails
        whenever(editPasswordRemoteDS.editPassword(editPasswordRequest)) doAnswer { throw Exception() }
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then failure resource should be emitted
        assertTrue(state is Resource.Failure)
    }

    @Test
    fun editPassword_EditSuccessful_ReturnFalseLoadingResource() = runTest {
        // Given edit password request
        val editPasswordRequest = editPasswordRequest

        // When edit is successful
        val state = editPasswordUC(editPasswordRequest).drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }

    @Test
    fun editPassword_EditFailed_ReturnFalseLoadingResource() = runTest {
        // Given edit password request
        val editPasswordRequest = editPasswordRequest

        // When edit fails
        whenever(editPasswordRemoteDS.editPassword(editPasswordRequest)) doAnswer { throw Exception() }
        val state = editPasswordUC(editPasswordRequest).drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }
    // endregion

    // region Old Password Validation Tests
    @Test
    fun editPassword_EmptyOldPassword_ReturnRequestValidationFailureResource() = runTest {
        // Given edit request with empty old password
        val editPasswordRequest = editPasswordRequest.copy(oldPassword = "")

        // When edit fails
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun editPassword_WhiteSpacesOldPassword_ReturnRequestValidationFailureResource() = runTest {
        // Given edit request with old password containing white spaces
        val editPasswordRequest = editPasswordRequest.copy(oldPassword = "    ")

        // When edit fails
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun editPassword_OldPasswordWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given edit request with old password containing white spaces
            val editPasswordRequest = editPasswordRequest.copy(oldPassword = "123 456")

            // When edit fails
            val state = editPasswordUC(editPasswordRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun editPassword_OldPasswordLessThanEightChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given edit request with old password less than eight characters
            val editPasswordRequest = editPasswordRequest.copy(oldPassword = "1234567")

            // When edit fails
            val state = editPasswordUC(editPasswordRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun editPassword_OldPasswordGreaterThanFiftyChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given edit request with old password greater than fifty characters
            val editPasswordRequest =
                editPasswordRequest.copy(oldPassword = "123456789012345678901234567890123456789012345678901")

            // When update fails
            val state = editPasswordUC(editPasswordRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region New Password Validation Tests
    @Test
    fun editPassword_EmptyNewPassword_ReturnRequestValidationFailureResource() = runTest {
        // Given edit request with empty new password
        val editPasswordRequest = editPasswordRequest.copy(newPassword = "")

        // When edit fails
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun editPassword_WhiteSpacesNewPassword_ReturnRequestValidationFailureResource() = runTest {
        // Given edit request with new password containing white spaces
        val editPasswordRequest = editPasswordRequest.copy(newPassword = "    ")

        // When edit fails
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun editPassword_NewPasswordWithWhiteSpaces_ReturnRequestValidationFailureResource() =
        runTest {
            // Given edit request with new password containing white spaces
            val editPasswordRequest = editPasswordRequest.copy(newPassword = "123 456")

            // When edit fails
            val state = editPasswordUC(editPasswordRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun editPassword_NewPasswordLessThanEightChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given edit request with new password less than eight characters
            val editPasswordRequest = editPasswordRequest.copy(newPassword = "1234567")

            // When edit fails
            val state = editPasswordUC(editPasswordRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }

    @Test
    fun editPassword_NewPasswordGreaterThanFiftyChars_ReturnRequestValidationFailureResource() =
        runTest {
            // Given edit request with new password greater than fifty characters
            val editPasswordRequest =
                editPasswordRequest.copy(newPassword = "123456789012345678901234567890123456789012345678901")

            // When update fails
            val state = editPasswordUC(editPasswordRequest).drop(1).first()

            // Then request validation failure resource should be emitted
            assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
        }
    // endregion

    // region Confirmation Password Validation Tests
    @Test
    fun editPassword_EmptyConfirmationPassword_ReturnRequestValidationFailureResource() = runTest {
        // Given edit request with empty confirmation password
        val editPasswordRequest = editPasswordRequest.copy(confirmPassword = "")

        // When edit fails
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }

    @Test
    fun editPassword_DifferentPassword_ReturnRequestValidationFailureResource() = runTest {
        // Given edit request with different confirmation password
        val editPasswordRequest = editPasswordRequest.copy(confirmPassword = "12345678")

        // When edit fails
        val state = editPasswordUC(editPasswordRequest).drop(1).first()

        // Then request validation failure resource should be emitted
        assertTrue((state as Resource.Failure).exception is AlTasheratException.Local.RequestValidation)
    }
    // endregion
}