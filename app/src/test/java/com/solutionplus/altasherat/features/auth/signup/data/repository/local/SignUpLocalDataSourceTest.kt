package com.solutionplus.altasherat.features.auth.signup.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.DataStoreKeyValueStorage
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SignUpLocalDataSourceTest {

    private val localDataSource = mockk<DataStoreKeyValueStorage>(relaxed = true)
    private lateinit var signUpLocalDataSource: SignUpLocalDataSource


    @Before
    fun setup() {
        signUpLocalDataSource = SignUpLocalDataSource(localDataSource)
    }

    @Test
    fun dataStore_getUserInfo_returnUserObject() = runTest {
        val userEntity = SignUpUserEntity(
            "token",
            "username",
            "firstname",
            "lastname",
            "email",
            "countryCode",
            "number"
        )

        val stringUser = Gson().toJson(userEntity)


        signUpLocalDataSource.saveUser(stringUser)

        val captor = slot<String>()

        coVerify { signUpLocalDataSource.saveUser(capture(captor)) }
        assertEquals(stringUser, captor.captured)
    }


}





