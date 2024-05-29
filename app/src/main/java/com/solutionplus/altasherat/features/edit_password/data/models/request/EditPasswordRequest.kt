package com.solutionplus.altasherat.features.edit_password.data.models.request

import com.google.gson.annotations.SerializedName

data class EditPasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("new_password_confirmation")
    val confirmPassword: String
)