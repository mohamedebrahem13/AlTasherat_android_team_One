package com.solutionplus.altasherat.features.home.contact_us.domain.repository

interface IContactUsRepository {
    suspend fun hasTokenKey(): Boolean

}