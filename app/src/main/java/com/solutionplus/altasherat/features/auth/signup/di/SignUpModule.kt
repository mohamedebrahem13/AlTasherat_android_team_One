package com.solutionplus.altasherat.features.auth.signup.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.data.repository.local.SignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.data.repository.remote.SignUpRemoteDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SaveLocalUserTokenUC
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SaveLocalUserUC
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SignUpUC
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidationUC
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object SignUpModule {


    @Provides
    fun provideSignUpRemoteDS(
        networkProvider: INetworkProvider
    ): ISignUpRemoteDataSource {
        return SignUpRemoteDataSource(networkProvider)
    }

    @Provides
    fun provideSignUpLocalDS(
        localProvider: IKeyValueStorageProvider,
    ): ISignUpLocalDataSource {
        return SignUpLocalDataSource(localProvider)
    }

    @Provides
    fun provideSignUpRepository(
        signUpRemoteDataSource: ISignUpRemoteDataSource,
        signUpLocalDataSource: ISignUpLocalDataSource
    ): ISignUpRepository {
        return SignUpRepository(signUpRemoteDataSource, signUpLocalDataSource)
    }

    @Provides
    fun provideUserInputValidationUC(): UserInputsValidationUC {
        return UserInputsValidationUC()
    }

    @Provides
    fun provideSignUpUC(
        signupRepository: SignUpRepository,
        userInputsValidationUC: UserInputsValidationUC,
    ): SignUpUC {
        return SignUpUC(
            signupRepository,
            userInputsValidationUC,
        )
    }

    @Provides
    fun provideSaveUserUC(
        signupRepository: SignUpRepository,
        encryptionService: IEncryptionService
    ): SaveLocalUserUC {
        return SaveLocalUserUC(signupRepository, encryptionService)
    }

    @Provides
    fun provideSaveUserTokenUC(
        signupRepository: SignUpRepository,
        encryptionService: IEncryptionService
    ): SaveLocalUserTokenUC {
        return SaveLocalUserTokenUC(signupRepository, encryptionService)
    }


}












