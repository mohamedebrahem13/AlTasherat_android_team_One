package com.solutionplus.altasherat.features.services.token.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.services.token.data.repository.TokenRepository
import com.solutionplus.altasherat.features.services.token.data.repository.local.TokenLocalDS
import com.solutionplus.altasherat.features.services.token.domain.interactor.GetCachedTokenUC
import com.solutionplus.altasherat.features.services.token.domain.repository.ITokenRepository
import com.solutionplus.altasherat.features.services.token.domain.repository.local.ITokenLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object TokenModule {

    @Provides
    fun provideTokenLocalDS(
        keyValueStorageProvider: IKeyValueStorageProvider,
        encryptionService: IEncryptionService
    ): ITokenLocalDS {
        return TokenLocalDS(
            keyValueStorage = keyValueStorageProvider,
            encryptionService = encryptionService
        )
    }

    @Provides
    fun provideTokenRepository(tokenLocalDS: ITokenLocalDS): ITokenRepository {
        return TokenRepository(localDS = tokenLocalDS)
    }

    @Provides
    fun provideGetCachedTokenUC(tokenRepository: ITokenRepository): GetCachedTokenUC {
        return GetCachedTokenUC(tokenRepository)
    }
}