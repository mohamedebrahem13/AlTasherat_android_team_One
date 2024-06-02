package com.solutionplus.altasherat.features.account.delete_account.di

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.account.delete_account.data.repository.DeleteAccountRepository
import com.solutionplus.altasherat.features.account.delete_account.data.repository.remote.DeleteAccountRemoteDS
import com.solutionplus.altasherat.features.account.delete_account.domain.interactor.DeleteAccountUC
import com.solutionplus.altasherat.features.account.delete_account.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.account.delete_account.domain.repository.remote.IDeleteAccountRemoteDS
import com.solutionplus.altasherat.features.services.token.domain.interactor.DeleteCachedTokenUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.DeleteCachedUserUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object DeleteAccountModule {

    @Provides
    fun provideDeleteAccountRemoteDS(networkProvider: INetworkProvider): IDeleteAccountRemoteDS {
        return DeleteAccountRemoteDS(networkProvider = networkProvider)
    }

    @Provides
    fun provideDeleteAccountRepository(remoteDS: IDeleteAccountRemoteDS): IDeleteAccountRepository {
        return DeleteAccountRepository(remoteDS = remoteDS)
    }

    @Provides
    fun provideDeleteAccountUC(
        repository: IDeleteAccountRepository,
        deleteCachedUserUC: DeleteCachedUserUC,
        deleteCachedTokenUC: DeleteCachedTokenUC
    ): DeleteAccountUC {
        return DeleteAccountUC(
            repository = repository,
            deleteCachedUserUC = deleteCachedUserUC,
            deleteCachedTokenUC = deleteCachedTokenUC
        )
    }
}