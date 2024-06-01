package com.solutionplus.altasherat.features.splash.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.repository.SplashRepository
import com.solutionplus.altasherat.features.splash.data.repository.local.SplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.interactor.GetUserPreferredCountryUC
import com.solutionplus.altasherat.features.splash.domain.interactor.HasCountryStringKeyUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.IsOnboardingShownUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.SaveUserPreferenceUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.SetOnboardingShownUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object SplashModule {

    @Provides
    fun provideLocalCountriesDataSource(preferenceStorage: IKeyValueStorageProvider): ISplashLocalDS {
        return SplashLocalDS(preferenceStorage)
    }

    @Provides
    fun provideCountriesRepository(
        localDataSource: ISplashLocalDS,

    ): ISplashRepository {
        return SplashRepository(localDataSource)
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
    @Provides
    fun provideGetUserPreferredCountryUC(
        splashRepository: ISplashRepository
    ): GetUserPreferredCountryUC {
        return GetUserPreferredCountryUC(splashRepository)
    }
}