package com.solutionplus.altasherat.features.personal_info.data.models.request

import com.google.gson.annotations.SerializedName

data class UpdateInfoRequest(
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("middlename")
    val middlename: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("birthdate")
    val birthDate: String?,
    @SerializedName("phone")
    val phone: PhoneRequest?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("country")
    val countryId: Int?,
)
