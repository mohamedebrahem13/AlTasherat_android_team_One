package com.solutionplus.altasherat.features.auth.signup.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpUserResponse
import com.solutionplus.altasherat.features.auth.signup.data.model.request.PhoneSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*


class SignUpRemoteDataSourceTest {

    private val networkProvider = mockk<INetworkProvider>(relaxed = true)
    private lateinit var signUpRemoteDataSource: SignUpRemoteDataSource

    @Before
    fun setUp() {
        signUpRemoteDataSource = SignUpRemoteDataSource(networkProvider)
    }

    @Test
    fun retrofit_signup_success() = runTest {
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
            networkProvider.post(
                responseWrappedModel = userResponse::class.java,
                pathUrl = "signup",
                headers = hashMapOf("accept" to "application/json"),
                requestBody = userRequest
            ) ?: SignUpResponseDto()
        } returns userResponse

        val result = signUpRemoteDataSource.signup(userRequest)

        assertEquals(result, userResponse)

    }

    @Test
    fun retrofit_signup_exception() = runTest {
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
        val expectedError = Exception("Network error")
        coEvery {
            networkProvider.post(
                responseWrappedModel = userResponse::class.java,
                pathUrl = "signup",
                headers = hashMapOf("accept" to "application/json"),
                requestBody = userRequest
            ) ?: SignUpResponseDto()
        } throws expectedError

        val caughtException = assertThrows(expectedError::class.java) {
            runBlocking { signUpRemoteDataSource.signup(userRequest) }
        }
        assertEquals(expectedError, caughtException)

    }
}











