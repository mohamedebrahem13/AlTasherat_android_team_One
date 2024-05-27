package com.solutionplus.altasherat.features.home.profile.di

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.home.profile.data.repository.ProfileRepository
import com.solutionplus.altasherat.features.home.profile.data.repository.remote.ProfileRemoteDataSource
import com.solutionplus.altasherat.features.home.profile.domain.intractor.LogoutUC
import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository
import com.solutionplus.altasherat.features.home.profile.domain.repository.remote.IProfileRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ProfileRemoteDataSourceModule {
    @Provides
    fun provideProfileRemoteDataSource(networkProvider: INetworkProvider): IProfileRemoteDataSource {
        return ProfileRemoteDataSource(networkProvider)
    }
    @Provides
   fun provideProfileRepository(profileRemoteDataSource:IProfileRemoteDataSource):IProfileRepository{
        return ProfileRepository(profileRemoteDataSource)
   }
    @Provides
    fun provideLogoutUC(repository: IProfileRepository): LogoutUC {
        return LogoutUC(repository)
    }

}