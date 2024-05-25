package com.solutionplus.altasherat.features.auth.login.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.data.repository.local.LoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.data.repository.remote.LoginRemoteDataSource
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginWithPhoneUC
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
        encryptionService: IEncryptionService

    ): ILoginLocalDataSource {
        return LoginLocalDataSource(localProvider, encryptionService)
    }

    @Provides
    fun provideLoginRepository(
        loginRemoteDataSource: ILoginRemoteDataSource,
        localLoginDataSource: ILoginLocalDataSource,
    ): ILoginRepository {
        return LoginRepository(loginRemoteDataSource, localLoginDataSource)
    }

    @Provides
    fun provideSLoginUC(
        loginRepository: LoginRepository,
    ): LoginWithPhoneUC {
        return LoginWithPhoneUC(loginRepository)
    }



}