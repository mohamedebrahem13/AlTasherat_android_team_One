package com.solutionplus.altasherat.features.auth.signup.domain.di

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.UserResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.data.repository.remote.SignUpRemoteDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SignUpUC
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


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
    fun provideSignUpRepository(
        signUpRemoteDataSource: ISignUpRemoteDataSource
    ): ISignUpRepository {
        return SignUpRepository(signUpRemoteDataSource)
    }

    @Provides
    fun provideSignUpUC(
        signupRepository: SignUpRepository
    ): SignUpUC {
        return SignUpUC(signupRepository)
    }


}












