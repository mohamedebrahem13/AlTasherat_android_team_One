package com.solutionplus.altasherat.features.services.user.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.services.user.data.repository.UserRepository
import com.solutionplus.altasherat.features.services.user.data.repository.local.UserLocalDS
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UserModule {

    @Provides
    fun provideUserRepository(localDs: IUserLocalDS): IUserRepository {
        return UserRepository(localDS = localDs)
    }

    @Provides
    fun provideUserLocalDS(
        keyStorageProvider: IKeyValueStorageProvider,
        encryptionService: IEncryptionService
    ): IUserLocalDS {
        return UserLocalDS(
            keyStorageProvider = keyStorageProvider,
            encryptionService = encryptionService
        )
    }

    @Provides
    fun provideGetCacheUserUC(repository: IUserRepository): GetCachedUserUC {
        return GetCachedUserUC(repository = repository)
    }
}