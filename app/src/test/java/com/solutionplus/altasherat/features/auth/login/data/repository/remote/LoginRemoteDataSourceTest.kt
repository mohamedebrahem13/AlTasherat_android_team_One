package com.solutionplus.altasherat.features.auth.login.data.repository.remote

import com.solutionplus.altasherat.common.data.repository.remote.RetrofitNetworkProvider
import com.solutionplus.altasherat.features.services.user.data.models.dto.ResponseDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginRemoteDataSourceTest {

    private val networkProvider = mockk<RetrofitNetworkProvider>(relaxed = true)
    private lateinit var loginRemoteDataSource: LoginRemoteDataSource

    @Before
    fun setUp() {
        loginRemoteDataSource = LoginRemoteDataSource(networkProvider)
    }

    @Test
    fun retrofit_login_success() = runTest {
        val phoneRequest = PhoneLoginRequest("0020", "4452233699")
        val userRequest = UserLoginRequest(
            "email989@gmail.com",
            "1111255569",
            phoneLoginRequest = phoneRequest
        )
        val userResponse = ResponseDto(
            message = "Signup is done successfully,",
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9",
            UserDto(firstname = "Ebram")
        )
        coEvery {
            networkProvider.post(
                responseWrappedModel = userResponse::class.java,
                pathUrl = "login",
                headers = hashMapOf("accept" to "application/json"),
                requestBody = userRequest
            ) ?: ResponseDto()
        } returns userResponse

        val result = loginRemoteDataSource.loginWithPhone(userRequest)

        assertEquals(result, userResponse)

    }

    @Test
    fun retrofit_signup_exception() = runTest {
        val phoneRequest = PhoneLoginRequest("0020", "4452233699")
        val userRequest = UserLoginRequest(
            "email989@gmail.com",
            "1111255569",
            phoneLoginRequest = phoneRequest
        )
        val userResponse = ResponseDto(
            message = "Signup is done successfully,",
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9",
            UserDto(firstname = "Ebram")
        )
        val expectedError = Exception("Network error")
        coEvery {
            networkProvider.post(
                responseWrappedModel = userResponse::class.java,
                pathUrl = "login",
                headers = hashMapOf("accept" to "application/json"),
                requestBody = userRequest
            ) ?: ResponseDto()
        } throws expectedError

        val caughtException = assertThrows(expectedError::class.java) {
            runBlocking { loginRemoteDataSource.loginWithPhone(userRequest) }
        }
        assertEquals(expectedError, caughtException)

    }
}