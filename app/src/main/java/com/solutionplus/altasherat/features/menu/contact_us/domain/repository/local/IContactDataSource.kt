package com.solutionplus.altasherat.features.menu.contact_us.domain.repository.local

internal interface IContactDataSource {
    suspend fun hasTokenKey(): Boolean

}