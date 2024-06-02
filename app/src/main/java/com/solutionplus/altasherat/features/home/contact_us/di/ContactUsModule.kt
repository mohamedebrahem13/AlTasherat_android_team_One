package com.solutionplus.altasherat.features.home.contact_us.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.home.contact_us.data.repository.ContactUsRepository
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.local.IContactDataSource
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.IContactUsRepository
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.intractor.CheckTokenKeyUC
import com.solutionplus.altasherat.features.home.contact_us.data.repository.local.ContactUsLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object ContactUsModule {
    @Provides
    fun provideContactUsLocalDS(localProvider: IKeyValueStorageProvider): IContactDataSource {
        return ContactUsLocalDS(localProvider)
    }
    @Provides
    fun provideContactUsLocalRepository( contactUsLocalDS: IContactDataSource): IContactUsRepository {
        return ContactUsRepository(contactUsLocalDS)
    }
    @Provides
    fun provideCheckTokenKeyUC(repository: IContactUsRepository): CheckTokenKeyUC {
        return CheckTokenKeyUC(repository)
    }

}