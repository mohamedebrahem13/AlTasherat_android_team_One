package com.solutionplus.altasherat.features.home.contact_us.data.repository.local

internal interface IContactUsLocalDataSource {
    suspend fun hasTokenKey(): Boolean

}