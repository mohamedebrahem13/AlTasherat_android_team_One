package com.solutionplus.altasherat.common.di

import android.content.Context
import com.solutionplus.altasherat.common.data.repository.local.DataStoreKeyValueStorage
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): IKeyValueStorageProvider =
        DataStoreKeyValueStorage(context)
}