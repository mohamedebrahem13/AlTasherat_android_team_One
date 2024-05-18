package com.solutionplus.altasherat.features.personal_info.data.models.entity

import com.google.gson.annotations.SerializedName

internal data class UserEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("middlename")
    val middlename: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: PhoneEntity,
    @SerializedName("image")
    val image: ImageEntity,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("email_verified")
    val isEmailVerified: Boolean,
    @SerializedName("phone_verified")
    val isPhoneVerified: Boolean,
    @SerializedName("is_blocked")
    val isBlocked: Int,
)