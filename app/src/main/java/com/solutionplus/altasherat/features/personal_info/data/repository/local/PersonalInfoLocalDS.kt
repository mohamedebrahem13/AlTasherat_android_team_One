package com.solutionplus.altasherat.features.personal_info.data.repository.local

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.personal_info.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.personal_info.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.personal_info.domain.repository.local.IPersonalInfoLocalDS

internal class PersonalInfoLocalDS(
    private val keyValueStorageProvider: IKeyValueStorageProvider,
    private val context: Context,
) : IPersonalInfoLocalDS {
    override fun getUserPersonalInfo(): UserEntity {
        val jsonString = context.assets.open("User.json").bufferedReader().use { it.readText() }
        return jsonString.getModelFromJSON(UserEntity::class.java)
    }

    override fun getCountries(): List<CountryEntity> {
        val jsonString =
            context.assets.open("Countries.json").bufferedReader().use { it.readText() }
        val itemType = object : TypeToken<List<CountryEntity>>() {}.type
        return jsonString.getModelFromJSON(itemType)
    }
}