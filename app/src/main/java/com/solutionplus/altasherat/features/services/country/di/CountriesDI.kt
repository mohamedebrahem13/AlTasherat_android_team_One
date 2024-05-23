package com.solutionplus.altasherat.features.services.country.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.country.data.repository.CountriesRepository
import com.solutionplus.altasherat.features.services.country.data.repository.local.CountriesLocalDS
import com.solutionplus.altasherat.features.services.country.data.repository.remote.CountriesRemoteDS
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object CountriesDI {

    @Provides
    fun provideCountriesRemoteDS(networkProvider: INetworkProvider): ICountriesRemoteDS {
        return CountriesRemoteDS(networkProvider)
    }

    @Provides
    fun provideCountriesLocalDS(keyValueStorageProvider: IKeyValueStorageProvider): ICountriesLocalDS {
        return CountriesLocalDS(keyValueStorageProvider)
    }

    @Provides
    fun provideCountriesRepository(
        remoteDS: ICountriesRemoteDS,
        localDS: ICountriesLocalDS
    ): ICountriesRepository {
        return CountriesRepository(remoteDS, localDS)
    }

    @Provides
    fun provideGetCachedCountriesUC(repository: ICountriesRepository): GetCachedCountriesUC {
        return GetCachedCountriesUC(repository)
    }
}