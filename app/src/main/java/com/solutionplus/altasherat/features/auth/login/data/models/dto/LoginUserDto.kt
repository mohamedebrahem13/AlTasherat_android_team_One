package com.solutionplus.altasherat.features.auth.login.data.models.dto

import android.provider.MediaStore.Images.Media
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class LoginUserDto(
    @SerializedName("birth_date")
    val birthDate: LocalDate? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("email_verified")
    val emailVerified: Boolean? = null,
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: Media? = null,
    @SerializedName("is_blocked")
    val isBlocked: Int? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("middlename")
    val middleName: String? = null,
    @SerializedName("phone")
    val phone: LoginPhoneDto? = null,
    @SerializedName("phone_verified")
    val isPhoneVerified: Boolean? = null,
    @SerializedName("username")
    val username: String? = null
)