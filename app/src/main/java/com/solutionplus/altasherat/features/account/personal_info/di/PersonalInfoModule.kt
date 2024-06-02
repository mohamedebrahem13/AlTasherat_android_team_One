package com.solutionplus.altasherat.features.account.personal_info.di

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.account.personal_info.data.repository.PersonalInfoRepository
import com.solutionplus.altasherat.features.account.personal_info.data.repository.remote.PersonalInfoRemoteDS
import com.solutionplus.altasherat.features.account.personal_info.domain.interactor.UpdatePersonalInfoUC
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.remote.IPersonalInfoRemoteDS
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
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
    fun provideRepository(
        remoteDS: IPersonalInfoRemoteDS,
    ): IPersonalInfoRepository {
        return PersonalInfoRepository(remoteDS = remoteDS)
    }

    @Provides
    fun provideUpdatePersonalInfoUC(
        repository: IPersonalInfoRepository,
        saveUserUC: SaveUserUC
    ): UpdatePersonalInfoUC {
        return UpdatePersonalInfoUC(
            repository = repository,
            saveUserUC = saveUserUC
        )
    }
}