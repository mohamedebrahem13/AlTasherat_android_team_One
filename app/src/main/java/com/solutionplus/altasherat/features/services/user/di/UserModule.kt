package com.solutionplus.altasherat.features.services.user.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.user.data.repository.UserRepository
import com.solutionplus.altasherat.features.services.user.data.repository.local.UserLocalDS
import com.solutionplus.altasherat.features.services.user.data.repository.remote.UserRemoteDS
import com.solutionplus.altasherat.features.services.user.domain.interactor.DeleteCachedUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS
import com.solutionplus.altasherat.features.services.user.domain.repository.remote.IUserRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UserModule {

    @Provides
    fun provideUserRepository(
        localDs: IUserLocalDS,
        remoteDS: IUserRemoteDS
    ): IUserRepository {
        return UserRepository(localDS = localDs, remoteDS = remoteDS)
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
    fun provideUserRemoteDS(
        networkProvider: INetworkProvider
    ): IUserRemoteDS {
        return UserRemoteDS(networkProvider = networkProvider)
    }

    @Provides
    fun provideGetUserUC(repository: IUserRepository): GetUserUC {
        return GetUserUC(repository = repository)
    }

    @Provides
    fun provideGetCacheUserUC(repository: IUserRepository): GetCachedUserUC {
        return GetCachedUserUC(repository = repository)
    }

    @Provides
    fun provideSaveUserUC(repository: IUserRepository): SaveUserUC {
        return SaveUserUC(repository = repository)
    }

    @Provides
    fun provideDeleteCachedUserUC(repository: IUserRepository): DeleteCachedUserUC {
        return DeleteCachedUserUC(repository = repository)
    }
}