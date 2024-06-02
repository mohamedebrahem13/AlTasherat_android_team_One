package com.solutionplus.altasherat.features.menu.contact_us.domain.repository

interface IContactUsRepository {
    suspend fun hasTokenKey(): Boolean

}