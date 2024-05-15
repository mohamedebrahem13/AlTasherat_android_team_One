package com.solutionplus.altasherat.features.splash.di

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.data.repository.CountriesRepository
import com.solutionplus.altasherat.features.splash.data.repository.local.CountriesLocalDS
import com.solutionplus.altasherat.features.splash.data.repository.remote.CountriesRemoteDS
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ICountriesRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object CountriesModule {


    @Provides
    @ViewModelScoped
    fun provideRemoteCountriesDataSource(iNetworkProvider: INetworkProvider): ICountriesRemoteDS {
        return CountriesRemoteDS(iNetworkProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideLocalCountriesDataSource(preferenceStorage: IKeyValueStorageProvider): ICountriesLocalDS {
        return CountriesLocalDS(preferenceStorage)
    }

    @Provides
    @ViewModelScoped
    fun provideCountriesRepository(
        remoteDataSource: ICountriesRemoteDS,
        localDataSource: ICountriesLocalDS
        , countryMapper: CountryMapper

    ): ICountriesRepository {
        return CountriesRepository(localDataSource, remoteDataSource,countryMapper)
    }
    @Provides
    @Singleton
    fun provideGetAndSaveCountriesUseCase(
        countriesRepository: ICountriesRepository
    ): BaseUseCase<Unit, Unit> {
        return GetAndSaveCountriesUseCase(countriesRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideCountryMapper(): CountryMapper {
        return CountryMapper
    }
}