package com.solutionplus.altasherat.features.auth.signup.data.model.dto

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("birth_date")
    val birthDate: Any? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("email_verified")
    val emailVerified: Boolean? = null,
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: Any? = null,
    @SerializedName("is_blocked")
    val isBlocked: Int? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("middlename")
    val middleName: String? = null,
    @SerializedName("phone")
    val phone: Phone? = null,
    @SerializedName("phone_verified")
    val phoneVerified: Boolean? = null,
    @SerializedName("username")
    val username: String? = null
)