package com.solutionplus.altasherat.features.auth.signup.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.data.repository.local.SignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.data.repository.remote.SignUpRemoteDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SignUpUC
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object SignUpModule {


    @Provides
    fun provideSignUpRemoteDS(
        networkProvider: INetworkProvider
    ): ISignUpRemoteDataSource {
        return SignUpRemoteDataSource(networkProvider)
    }

    @Provides
    fun provideSignUpLocalDS(
        localProvider: IKeyValueStorageProvider,
        encryptionService: IEncryptionService
    ): ISignUpLocalDataSource {
        return SignUpLocalDataSource(localProvider, encryptionService)
    }

    @Provides
    fun provideSignUpRepository(
        signUpRemoteDataSource: ISignUpRemoteDataSource,
        signUpLocalDataSource: ISignUpLocalDataSource,
    ): ISignUpRepository {
        return SignUpRepository(signUpRemoteDataSource, signUpLocalDataSource)
    }


    @Provides
    fun provideSignUpUC(
        signupRepository: SignUpRepository,
        saveUserUC: SaveUserUC
    ): SignUpUC {
        return SignUpUC(
            signupRepository,
            saveUserUC
        )
    }

}












