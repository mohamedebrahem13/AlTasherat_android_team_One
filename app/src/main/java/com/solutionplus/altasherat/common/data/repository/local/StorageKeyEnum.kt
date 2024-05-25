package com.solutionplus.altasherat.common.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    USER_KEY("user_pref"),
    USER_TOKEN_KEY("user_token_key"),
    COUNTRIES_STRING("countries_string"),
    LANGUAGE("language"),
    USER_PREFERRED_COUNTRY("country"),
    ONBOARDING_SHOWN("onboarding_shown")

}