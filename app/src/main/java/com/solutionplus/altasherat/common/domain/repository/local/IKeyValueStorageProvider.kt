package com.solutionplus.altasherat.common.domain.repository.local

import java.lang.reflect.Type

interface IKeyValueStorageProvider {
    suspend fun <Model> save(key: IStorageKeyEnum, value: Model, type: Type)
    suspend fun <Model> get(key: IStorageKeyEnum, defaultValue: Model, type: Type): Model?
    suspend fun <Model> update(key: IStorageKeyEnum, value: Model, type: Type)
    suspend fun <Model> delete(key: IStorageKeyEnum, type: Type)
    suspend fun <Model> hasKey(key: IStorageKeyEnum, type: Type): Boolean
}