package com.solutionplus.altasherat.features.home.profile.domain.repository


interface IProfileRepository {
    suspend fun logout(): String
}