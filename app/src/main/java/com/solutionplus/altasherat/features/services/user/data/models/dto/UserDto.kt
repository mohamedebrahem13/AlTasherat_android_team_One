package com.solutionplus.altasherat.features.services.user.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class UserDto(
    @SerializedName("birthdate")
    val birthDate: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("email_verified")
    val emailVerified: Boolean? = null,
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: ImageDto? = null,
    @SerializedName("blocked")
    val isBlocked: Int? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("middlename")
    val middleName: String? = null,
    @SerializedName("phone")
    val phone: PhoneDto? = null,
    @SerializedName("phone_verified")
    val isPhoneVerified: Boolean? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("country")
    val country: CountryDto? = null,
    @SerializedName("all_permissions")
    val allPermissions: List<String>? = null,

    )