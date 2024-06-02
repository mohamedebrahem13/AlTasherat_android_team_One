package com.solutionplus.altasherat.features.home.contact_us.domain.repository.local

internal interface IContactDataSource {
    suspend fun hasTokenKey(): Boolean

}