package com.solutionplus.altasherat.features.splash.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.repository.SplashRepository
import com.solutionplus.altasherat.features.splash.data.repository.local.SplashLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.SplashRemoteDS
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.GetCountriesFromLocalUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.HasCountryStringKeyUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.IsOnboardingShownUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.SaveUserPreferenceUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.SetOnboardingShownUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object SplashModule {


    @Provides
    fun provideRemoteCountriesDataSource(iNetworkProvider: INetworkProvider): ISplashRemoteDS {
        return SplashRemoteDS(iNetworkProvider)
    }

    @Provides
    fun provideLocalCountriesDataSource(preferenceStorage: IKeyValueStorageProvider): ISplashLocalDS {
        return SplashLocalDS(preferenceStorage)
    }

    @Provides
    fun provideCountriesRepository(
        remoteDataSource: ISplashRemoteDS,
        localDataSource: ISplashLocalDS,
        countryMapper: CountryMapper

    ): ISplashRepository {
        return SplashRepository(localDataSource, remoteDataSource,countryMapper)
    }
    @Provides
    fun provideGetAndSaveCountriesUseCase(
        countriesRepository: ISplashRepository
    ): GetAndSaveCountriesUseCase{
        return GetAndSaveCountriesUseCase(countriesRepository)
    }
    @Provides
    fun provideGetCountriesFromLocalUseCase(
        splashRepository: ISplashRepository
    ): GetCountriesFromLocalUseCase{
        return GetCountriesFromLocalUseCase(splashRepository)
    }
    @Provides
    fun provideCountryMapper(): CountryMapper {
        return CountryMapper
    }
    @Provides
    fun provideSaveUserPreferenceUseCase(
        splashRepository: ISplashRepository
    ): SaveUserPreferenceUseCase {
        return SaveUserPreferenceUseCase(splashRepository)
    }
    @Provides
    fun provideHasCountryStringKeyUseCase(
        splashRepository: ISplashRepository
    ): HasCountryStringKeyUseCase {
        return HasCountryStringKeyUseCase(splashRepository)
    }
    @Provides
    fun provideIsOnboardingShownUseCase(
        splashRepository: ISplashRepository
    ): IsOnboardingShownUseCase {
        return IsOnboardingShownUseCase(splashRepository)
    }
    @Provides
    fun provideSetOnboardingShownUseCase(
        splashRepository: ISplashRepository
    ): SetOnboardingShownUseCase {
        return SetOnboardingShownUseCase(splashRepository)
    }
}