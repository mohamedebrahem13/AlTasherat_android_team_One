package com.solutionplus.altasherat.features.personal_info.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class UserDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("middlename")
    val middlename: String? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("phone")
    val phone: PhoneDto? = null,
    @SerializedName("image")
    val image: ImageDto? = null,
    @SerializedName("birthdate")
    val birthDate: String? = null,
    @SerializedName("email_verified")
    val isEmailVerified: Boolean? = null,
    @SerializedName("phone_verified")
    val isPhoneVerified: Boolean? = null,
    @SerializedName("blocked")
    val isBlocked: Int? = null,
    @SerializedName("country")
    val country: CountryDto? = null,
    @SerializedName("all_permissions")
    val allPermissions: List<String>? = null,
)