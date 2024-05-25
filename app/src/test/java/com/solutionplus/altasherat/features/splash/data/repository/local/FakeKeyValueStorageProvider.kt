package com.solutionplus.altasherat.features.splash.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum
import java.lang.reflect.Type

class FakeKeyValueStorageProvider : IKeyValueStorageProvider {

    private val storageMap = mutableMapOf<String, Any>()

    override suspend fun <Model> save(key: IStorageKeyEnum, value: Model, type: Type) {
        storageMap[key.keyValue] = value as Any
    }

    override suspend fun <Model> get(
        key: IStorageKeyEnum,
        defaultValue: Model,
        type: Type
    ): Model {
        @Suppress("UNCHECKED_CAST")
        val storedValue = storageMap[key.keyValue] as Model
        return storedValue ?: defaultValue
    }

    override suspend fun <Model> update(key: IStorageKeyEnum, value: Model, type: Type) {
    }

    override suspend fun <Model> delete(key: IStorageKeyEnum, type: Type) {
    }

    override suspend fun <Model> hasKey(key: IStorageKeyEnum, type: Type): Boolean {
        return storageMap.containsKey(key.keyValue)
    }
}