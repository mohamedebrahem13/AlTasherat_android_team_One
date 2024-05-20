package com.solutionplus.altasherat.common.di

import com.solutionplus.altasherat.common.data.repository.local.encryption.EncryptionService
import com.solutionplus.altasherat.common.data.repository.local.encryption.KeyStoreService
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IKeyStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EncryptionModule {

    @Provides
    fun providesKeyStoreService(): IKeyStoreService {
        return KeyStoreService()
    }

    @Provides
    fun providesEncryptionService(keyStoreService: IKeyStoreService): IEncryptionService {
        return EncryptionService(keyStoreService = keyStoreService)
    }
}