package com.solutionplus.altasherat.features.account.edit_password.di

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.account.edit_password.data.repository.EditPasswordRepository
import com.solutionplus.altasherat.features.account.edit_password.data.repository.remote.EditPasswordRemoteDS
import com.solutionplus.altasherat.features.account.edit_password.domain.interactor.EditPasswordUC
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.IEditPasswordRepository
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.remote.IEditPasswordRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object EditPasswordModule {

    @Provides
    fun provideEditPasswordRemoteDS(
        networkProvider: INetworkProvider
    ): IEditPasswordRemoteDS {
        return EditPasswordRemoteDS(networkProvider = networkProvider)
    }

    @Provides
    fun provideEditPasswordRepository(
        editPasswordRemoteDS: IEditPasswordRemoteDS
    ): IEditPasswordRepository {
        return EditPasswordRepository(remoteDS = editPasswordRemoteDS)
    }

    @Provides
    fun provideEditPasswordUC(
        editPasswordRepository: IEditPasswordRepository
    ): EditPasswordUC {
        return EditPasswordUC(repository = editPasswordRepository)
    }
}