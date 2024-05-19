package com.solutionplus.altasherat.features.splash.di

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.repository.SplashRepository
import com.solutionplus.altasherat.features.splash.data.repository.local.SplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.SplashRemoteDS
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object SplashModule {


    @Provides
    @ViewModelScoped
    fun provideRemoteCountriesDataSource(iNetworkProvider: INetworkProvider): ISplashRemoteDS {
        return SplashRemoteDS(iNetworkProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideLocalCountriesDataSource(preferenceStorage: IKeyValueStorageProvider): ISplashLocalDS {
        return SplashLocalDS(preferenceStorage)
    }

    @Provides
    @ViewModelScoped
    fun provideCountriesRepository(
        remoteDataSource: ISplashRemoteDS,
        localDataSource: ISplashLocalDS,
        countryMapper: CountryMapper

    ): ISplashRepository {
        return SplashRepository(localDataSource, remoteDataSource,countryMapper)
    }
    @Provides
    @Singleton
    fun provideGetAndSaveCountriesUseCase(
        countriesRepository: ISplashRepository
    ): BaseUseCase<Unit, Unit> {
        return GetAndSaveCountriesUseCase(countriesRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideCountryMapper(): CountryMapper {
        return CountryMapper
    }
}