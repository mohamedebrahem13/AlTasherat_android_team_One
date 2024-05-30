package com.solutionplus.altasherat.features.auth.login.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.data.repository.local.LoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.data.repository.remote.LoginRemoteDataSource
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginWithPhoneUC
import com.solutionplus.altasherat.features.auth.login.domain.interactor.validation.LoginInputValidation
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidationUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object LoginModule {


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
    fun provideUserInputValidationUC(): LoginInputValidation {
        return LoginInputValidation()
    }

    @Provides
    fun provideSLoginUC(
        loginRepository: ILoginRepository,
        saveUserUC: SaveUserUC,
        loginInputValidation: LoginInputValidation
    ): LoginWithPhoneUC {
        return LoginWithPhoneUC(loginRepository, saveUserUC, loginInputValidation)
    }



}