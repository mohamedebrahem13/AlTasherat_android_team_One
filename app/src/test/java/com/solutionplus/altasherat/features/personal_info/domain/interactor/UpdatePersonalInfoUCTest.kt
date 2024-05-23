package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.personal_info.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.data.repository.PersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.local.IPersonalInfoLocalDS
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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UpdatePersonalInfoUCTest {

    private lateinit var personalInfoLocalDS: IPersonalInfoLocalDS
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
            lastname = "El-Sayed",
            email = "ahmed.hassan@example.com",
            birthDate = "1985-05-15",
            phone = phoneRequest,
            image = "ahmed_el_sayed_profile.png",
            countryId = 1
        )

        personalInfoLocalDS = mock()
        updatePersonalInfoResponse = mock<UpdateInfoResponseDto> {
            on { message } doReturn "Updated successfully"
        }
        personalInfoRemoteDS = mock<IPersonalInfoRemoteDS> {
            onBlocking { updatePersonalInfo(updateInfoRequest) } doReturn updatePersonalInfoResponse
        }
        personalInfoRepository = PersonalInfoRepository(personalInfoRemoteDS, personalInfoLocalDS)
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

        // When login is successful
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then success resource should be emitted
        assertEquals(updatePersonalInfoResponse.message, (state as Resource.Success).model.message)
    }

    @Test
    fun updatePersonalInfo_UpdateFailed_ReturnFailureResource() = runTest {
        // Given update info request
        val updateInfoRequest = updateInfoRequest

        // When update fails
        whenever(personalInfoRemoteDS.updatePersonalInfo(updateInfoRequest)).thenThrow(Exception())
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then failure resource should be emitted
        assertTrue(state is Resource.Failure)
    }

    @Test
    fun updatePersonalInfo_UpdateSuccessful_ReturnFalseLoadingResource() = runTest {
        // Given login request
        val updateInfoRequest = updateInfoRequest

        // When login is successful
        whenever(personalInfoRemoteDS.updatePersonalInfo(updateInfoRequest)).thenThrow(Exception())
        val state = updatePersonalInfoUC(updateInfoRequest).drop(2).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }

    @Test
    fun updatePersonalInfo_UpdateFailed_ReturnFalseLoadingResource() = runTest {
        // Given login request
        val updateInfoRequest = updateInfoRequest

        // When login fails
        whenever(personalInfoRemoteDS.updatePersonalInfo(updateInfoRequest)).thenThrow(Exception())
        val state = updatePersonalInfoUC(updateInfoRequest).drop(1).first()

        // Then loading resource should be emitted
        assertFalse((state as Resource.Progress).loading)
    }
    // endregion
}