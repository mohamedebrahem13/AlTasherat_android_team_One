package com.solutionplus.altasherat.features.personal_info.di

import android.content.Context
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personal_info.data.repository.PersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.data.repository.local.PersonalInfoLocalDS
import com.solutionplus.altasherat.features.personal_info.data.repository.remote.PersonalInfoRemoteDS
import com.solutionplus.altasherat.features.personal_info.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.features.personal_info.domain.interactor.GetUserPersonalInfoUC
import com.solutionplus.altasherat.features.personal_info.domain.interactor.UpdatePersonalInfoUC
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.local.IPersonalInfoLocalDS
import com.solutionplus.altasherat.features.personal_info.domain.repository.remote.IPersonalInfoRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object PersonalInfoModule {

    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): IPersonalInfoRemoteDS {
        return PersonalInfoRemoteDS(networkProvider = networkProvider)
    }

    @Provides
    fun provideLocalDS(
        storageKeyValue: IKeyValueStorageProvider,
        context: Context
    ): IPersonalInfoLocalDS {
        return PersonalInfoLocalDS(keyValueStorageProvider = storageKeyValue, context = context)
    }

    @Provides
    fun provideRepository(
        remoteDS: IPersonalInfoRemoteDS,
        localDS: IPersonalInfoLocalDS,
    ): IPersonalInfoRepository {
        return PersonalInfoRepository(remoteDS = remoteDS, localDS = localDS)
    }

    @Provides
    fun provideGetUserPersonalInfoUC(repository: IPersonalInfoRepository): GetUserPersonalInfoUC {
        return GetUserPersonalInfoUC(repository = repository)
    }

    @Provides
    fun provideGetCountriesUC(repository: IPersonalInfoRepository): GetCountriesUC {
        return GetCountriesUC(repository = repository)
    }

    @Provides
    fun provideUpdatePersonalInfoUC(repository: IPersonalInfoRepository): UpdatePersonalInfoUC {
        return UpdatePersonalInfoUC(repository = repository)
    }
}