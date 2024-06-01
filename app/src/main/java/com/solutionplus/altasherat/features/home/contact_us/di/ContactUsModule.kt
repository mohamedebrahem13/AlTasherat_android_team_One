package com.solutionplus.altasherat.features.home.contact_us.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.home.contact_us.data.repository.ContactUsLocalRepository
import com.solutionplus.altasherat.features.home.contact_us.data.repository.local.IContactUsLocalDataSource
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.IContactUsRepository
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.intractor.CheckTokenKeyUC
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.local.ContactUsLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object ContactUsModule {
    @Provides
    fun provideContactUsLocalDS(localProvider: IKeyValueStorageProvider): IContactUsLocalDataSource {
        return ContactUsLocalDS(localProvider)
    }
    @Provides
    fun provideContactUsLocalRepository( contactUsLocalDS: IContactUsLocalDataSource): IContactUsRepository {
        return ContactUsLocalRepository(contactUsLocalDS)
    }
    @Provides
    fun provideCheckTokenKeyUC(repository: IContactUsRepository): CheckTokenKeyUC {
        return CheckTokenKeyUC(repository)
    }

}