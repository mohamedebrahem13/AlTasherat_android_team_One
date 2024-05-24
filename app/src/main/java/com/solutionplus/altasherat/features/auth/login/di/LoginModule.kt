package com.solutionplus.altasherat.features.auth.login.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.data.repository.local.LoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.data.repository.remote.LoginRemoteDataSource
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginUC
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {


    @Provides
    fun provideLoginRemoteDS(
        networkProvider: INetworkProvider
    ): ILoginRemoteDataSource {
        return LoginRemoteDataSource(networkProvider)
    }

    @Provides
    fun provideLoginUpLocalDS(
        localProvider: IKeyValueStorageProvider,
    ): ILoginLocalDataSource {
        return LoginLocalDataSource(localProvider)
    }

    @Provides
    fun provideLoginRepository(
        loginRemoteDataSource: ILoginRemoteDataSource,
        localLoginDataSource: ILoginLocalDataSource,
        encryptionService: IEncryptionService
    ): ILoginRepository {
        return LoginRepository(loginRemoteDataSource, localLoginDataSource, encryptionService)
    }

    @Provides
    fun provideSLoginUC(
        loginRepository: LoginRepository,
    ): LoginUC {
        return LoginUC(loginRepository)
    }



}