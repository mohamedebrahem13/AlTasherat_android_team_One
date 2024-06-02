package com.solutionplus.altasherat.features.home.contact_us.data.repository

import com.solutionplus.altasherat.features.home.contact_us.domain.repository.local.IContactDataSource
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.IContactUsRepository

internal class ContactUsRepository (private val localDataSource: IContactDataSource):IContactUsRepository{
    override suspend fun hasTokenKey(): Boolean {
        return localDataSource.hasTokenKey()
    }
}