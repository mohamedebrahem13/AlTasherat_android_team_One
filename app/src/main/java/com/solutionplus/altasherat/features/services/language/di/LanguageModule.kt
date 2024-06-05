package com.solutionplus.altasherat.features.services.language.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.language.data.repository.LanguageRepository
import com.solutionplus.altasherat.features.services.language.data.repository.local.LanguageLocalDS
import com.solutionplus.altasherat.features.services.language.domain.interactor.GetCachedLanguageUC
import com.solutionplus.altasherat.features.services.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LanguageModule {

    @Provides
    fun provideLanguageLocalDS(
        keyStorageProvider: IKeyValueStorageProvider
    ): ILanguageLocalDS {
        return LanguageLocalDS(keyStorageProvider = keyStorageProvider)
    }

    @Provides
    fun provideLanguageRepository(
        languageLocalDS: ILanguageLocalDS
    ): ILanguageRepository {
        return LanguageRepository(localDS = languageLocalDS)
    }

    @Provides
    fun getCachedLanguageUC(
        languageRepository: ILanguageRepository
    ): GetCachedLanguageUC {
        return GetCachedLanguageUC(repository = languageRepository)
    }
}