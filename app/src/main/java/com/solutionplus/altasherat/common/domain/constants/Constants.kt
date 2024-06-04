package com.solutionplus.altasherat.common.domain.constants

object Constants {
    const val FIRST_NAME = "firstname"
    const val MIDDLE_NAME = "middlename"
    const val LAST_NAME = "lastname"
    const val EMAIL = "email"
    const val PHONE = "phone"
    const val PHONE_NUMBER = "phone.number"
    const val OLD_PASSWORD = "old_password"
    const val NEW_PASSWORD = "new_password"
    const val NEW_PASSWORD_CONFIRMATION = "new_password_confirmation"
    const val PASSWORD = "password"
    const val COUNTRY = "country"
    const val BIRTH_DATE = "birthdate"

    const val FIRST_NAME_VALIDATION = "First name must be between 3 and 15 alphabetic characters."
    const val MIDDLE_NAME_VALIDATION = "Middle name must be between 0 and 15 alphabetic characters."
    const val LAST_NAME_VALIDATION = "Last name must be between 3 and 15 alphabetic characters."
    const val EMAIL_VALIDATION = "Email must be a valid email address and less than 50 characters."
    const val PHONE_VALIDATION = "Phone number must be a valid phone number."
    const val PHONE_NUMBER_VALIDATION = "Phone number must be between 9 and 15 digits."
    const val PASSWORD_VALIDATION = "Password must be between 8 and 50 characters."
    const val NEW_PASSWORD_VALIDATION =
        "New password must not be the same as the old password and must be between 8 and 50 characters."
    const val CONFIRM_PASSWORD_VALIDATION = "Password confirmation must match the password."
}