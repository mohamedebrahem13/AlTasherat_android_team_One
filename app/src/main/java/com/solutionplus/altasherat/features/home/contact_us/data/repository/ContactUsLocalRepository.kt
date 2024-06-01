package com.solutionplus.altasherat.features.home.contact_us.data.repository

import com.solutionplus.altasherat.features.home.contact_us.data.repository.local.IContactUsLocalDataSource
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.IContactUsRepository

internal class ContactUsLocalRepository (private val localDataSource: IContactUsLocalDataSource):IContactUsRepository{
    override suspend fun hasTokenKey(): Boolean {
        return localDataSource.hasTokenKey()
    }
}