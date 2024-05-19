package com.solutionplus.altasherat.common.data.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.JsonSerializer
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum
import kotlinx.coroutines.flow.first
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class DataStoreKeyValueStorage(private val context: Context) : IKeyValueStorageProvider {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    override suspend fun <Model> save(key: IStorageKeyEnum, value: Model, type: Type) {
        val preferencesKey = getPreferenceKey<Model>(key, type)
        context.dataStore.edit {
            it[preferencesKey] = value
        }
    }

    override suspend fun <Model> get(
        key: IStorageKeyEnum,
        defaultValue: Model,
        type: Type
    ): Model? {
        val preferencesKey = getPreferenceKey<Model>(key, type)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey] ?: defaultValue
    }

    override suspend fun <Model> update(key: IStorageKeyEnum, value: Model, type: Type) {
        val preferencesKey = getPreferenceKey<Model>(key, type)
        context.dataStore.edit {
            it[preferencesKey] = value
        }
    }

    override suspend fun <Model> delete(key: IStorageKeyEnum, type: Type) {
        val preferencesKey = getPreferenceKey<Model>(key, type)
        context.dataStore.edit {
            it.remove(preferencesKey)
        }
    }

    override suspend fun <Model> hasKey(key: IStorageKeyEnum, type: Type): Boolean {
        val preferencesKey = getPreferenceKey<Model>(key, type)
        val preferences = context.dataStore.data.first()
        return preferences.contains(preferencesKey)
    }

    private fun <Model> getPreferenceKey(key: IStorageKeyEnum, type: Type): Preferences.Key<Model> {
        return when (type) {
            Boolean::class.java -> booleanPreferencesKey(key.keyValue)
            Float::class.java -> floatPreferencesKey(key.keyValue)
            Int::class.java -> intPreferencesKey(key.keyValue)
            Long::class.java -> longPreferencesKey(key.keyValue)
            String::class.java -> stringPreferencesKey(key.keyValue)
            Set::class.java -> stringSetPreferencesKey(key.keyValue)
            else -> throw AlTasheratException.Local.IOOperation(R.string.unsupported_type)
        } as Preferences.Key<Model>

    }

    companion object {
        private const val DATA_STORE_NAME = "altasherat_user_data"
    }
}
