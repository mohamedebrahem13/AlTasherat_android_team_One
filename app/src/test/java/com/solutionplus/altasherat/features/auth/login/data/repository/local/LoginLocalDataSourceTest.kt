package com.solutionplus.altasherat.features.auth.login.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.DataStoreKeyValueStorage
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.features.auth.login.data.models.entity.LoginUserEntity
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginLocalDataSourceTest{

    private val localDataSource = mockk<DataStoreKeyValueStorage>(relaxed = true)
    private lateinit var loginLocalDataSource: LoginLocalDataSource

    @Before
    fun setup() {
        loginLocalDataSource = LoginLocalDataSource(localDataSource)
    }

    @Test
    fun dataStore_getUserInfo_returnUserObject() = runTest {
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
        loginLocalDataSource.saveUser(stringUser)

        val captor = slot<String>()

        coVerify {
            localDataSource.save(
                StorageKeyEnum.USER_KEY,
                capture(captor),
                String::class.java
            )
        }
        assertEquals(stringUser, captor.captured)
    }
}

