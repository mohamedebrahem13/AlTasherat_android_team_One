package com.solutionplus.altasherat.features.auth.login.data.repository

import com.google.gson.Gson
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginUserResponse
import com.solutionplus.altasherat.features.auth.login.data.models.entity.LoginUserEntity
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.repository.local.LoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.data.repository.remote.LoginRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginRepositoryTest {

    private val loginLocalDataSource = mockk<LoginLocalDataSource>(relaxed = true)
    private val loginRemoteDataSource = mockk<LoginRemoteDataSource>(relaxed = true)
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setup() {
        loginRepository = LoginRepository(loginRemoteDataSource, loginLocalDataSource)
    }

    @Test
    fun repository_login_returnUserInfo() = runTest {
        val phoneRequest = PhoneLoginRequest("0020", "4452233699")
        val userRequest = UserLoginRequest(
            "email989@gmail.com",
            password = "1111255569",
            phoneRequest
        )
        val userResponse = LoginResponseDto(
            message = "Signup is done successfully,",
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9",
            LoginUserResponse(firstname = "Ebram")
        )
        coEvery {
            loginRemoteDataSource.loginWithPhone(userRequest)
        } returns userResponse

        val result = loginRemoteDataSource.loginWithPhone(userRequest)

        assertEquals(result, userResponse)

    }

    @Test
    fun repository_saveUser_checkSaveUserMethod() = runTest {
        val userEntity = LoginUserEntity(
            "token",
            "username",
            "firstname",
            "lastname",
            "email",
            "countryCode",
            "number"
        )
        val stringUser = Gson().toJson(userEntity)
        loginRepository.saveUser(stringUser)

        val captor = slot<String>()
        coVerify { loginLocalDataSource.saveUser(capture(captor)) }

        assertEquals(stringUser, captor.captured)

    }


    @Test
    fun repository_saveUser_checkSaveTokenMethod() = runTest {
        val userEntity = LoginUserEntity(
            "token",
            "username",
            "firstname",
            "lastname",
            "email",
            "countryCode",
            "number"
        )
        loginRepository.saveUserToken(userEntity.token)

        val captor = slot<String>()
        coVerify { loginLocalDataSource.saveToken(capture(captor)) }

        assertEquals(userEntity.token, captor.captured)

    }
}