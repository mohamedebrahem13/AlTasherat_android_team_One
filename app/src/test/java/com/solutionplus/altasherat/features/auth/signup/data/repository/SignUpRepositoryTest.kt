package com.solutionplus.altasherat.features.auth.signup.data.repository

import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.signup.data.mapper.SignUpMapper
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpUserResponse
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignUpRepositoryTest {

    private val signUpLocalDataSource = mockk<ISignUpLocalDataSource>(relaxed = true)
    private val signUpRemoteDataSource = mockk<ISignUpRemoteDataSource>(relaxed = true)
    private val encryptionService = mockk<IEncryptionService>(relaxed = true)
    private lateinit var signUpRepository: SignUpRepository

    @Before
    fun setup() {
        signUpRepository = SignUpRepository(signUpRemoteDataSource, signUpLocalDataSource, encryptionService)
    }

    @Test
    fun repository_signup_returnUserInfo() = runTest {
        val phoneRequest = PhoneSignUpRequest("0020", "4452233699")
        val userRequest = UserSignUpRequest(
            "Ebram",
            "Ibrahem",
            "Aziz",
            "email989@gmail.com",
            "1111255569",
            "1111255569",
            1,
            phoneRequest
        )
        val userResponse = SignUpResponseDto(
            message = "Signup is done successfully,",
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9",
            SignUpUserResponse(firstname = "Ebram")
        )
        coEvery {
            signUpRemoteDataSource.signup(userRequest)
        } returns userResponse

        val result = signUpRemoteDataSource.signup(userRequest)

        assertEquals(result, userResponse)

    }

    @Test
    fun repository_saveUser_checkSaveUserMethod() = runTest {
        val userEntity = SignUpUserEntity(
            "token",
            "username",
            "firstname",
            "lastname",
            "email",
            "countryCode",
            "number"
        )
        val userInfo = SignUpMapper.entityToDomain(userEntity)
        signUpRepository.saveUser(userInfo)

        val captor = slot<String>()
        coVerify { signUpLocalDataSource.saveUser(capture(captor)) }

        assertEquals(userInfo, captor.captured)

    }


    @Test
    fun repository_saveUser_checkSaveTokenMethod() = runTest {
        val userEntity = SignUpUserEntity(
            "token",
            "username",
            "firstname",
            "lastname",
            "email",
            "countryCode",
            "number"
        )
        val userInfo = SignUpMapper.entityToDomain(userEntity)
        signUpRepository.saveUserToken(userInfo)

        val captor = slot<String>()
        coVerify { signUpLocalDataSource.saveUserToken(capture(captor)) }

        assertEquals(userInfo.token, captor.captured)

    }
}












