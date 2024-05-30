package com.solutionplus.altasherat.features.home.language.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.home.language.data.repository.LanguageRepository
import com.solutionplus.altasherat.features.home.language.data.repository.local.LanguageLocalDS
import com.solutionplus.altasherat.features.home.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.home.language.domain.repository.interactor.SaveUserPreferenceLanguageUseCase
import com.solutionplus.altasherat.features.home.language.domain.repository.local.ILanguageLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object LanguageSettingsModule {
    @Provides
    fun provideLocalLanguageDataSource(preferenceStorage: IKeyValueStorageProvider): ILanguageLocalDS {
        return LanguageLocalDS(preferenceStorage)
    }

    @Provides
    fun provideLanguageRepository(
        localDataSource: ILanguageLocalDS): ILanguageRepository {
        return LanguageRepository(localDataSource)
    }
    @Provides
    fun provideSaveUserPreferenceLanguageUseCase(
        languageRepository: ILanguageRepository
    ): SaveUserPreferenceLanguageUseCase {
        return SaveUserPreferenceLanguageUseCase(languageRepository)
    }
}