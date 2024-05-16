package com.solutionplus.altasherat.features.auth.login.domain.di

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.data.repository.remote.LoginRemoteDataSource
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginUC
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
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
    fun provideLoginRepository(
        loginRemoteDataSource: ILoginRemoteDataSource
    ): ILoginRepository {
        return LoginRepository(loginRemoteDataSource)
    }

    @Provides
    fun provideSLoginUC(
        loginRepository: LoginRepository
    ): LoginUC {
        return LoginUC(loginRepository)
    }

}