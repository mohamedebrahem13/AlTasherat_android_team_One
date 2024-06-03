package com.solutionplus.altasherat.features.account.edit_password.data.models.request

import com.google.gson.annotations.SerializedName

data class EditPasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("new_password_confirmation")
    val confirmPassword: String
) {
    fun isOldPasswordValid(): Boolean {
        return oldPassword.isNotEmpty() && oldPassword.length >= 8 && oldPassword.length <= 50
    }

    fun isNewPasswordValid(): Boolean {
        return newPassword.isNotEmpty() && newPassword.length >= 8 && newPassword.length <= 50 && newPassword != oldPassword
    }

    fun isConfirmPasswordValid(): Boolean {
        return confirmPassword == newPassword
    }
}