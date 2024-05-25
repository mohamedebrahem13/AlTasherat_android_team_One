package com.solutionplus.altasherat.common.data.repository.local.encryption

import com.solutionplus.altasherat.common.domain.repository.local.encryption.ISecretKeyAliasEnum

enum class SecretKeyAliasEnum(override val keyAlias: String) : ISecretKeyAliasEnum {
    USER("encryption_user_preference"),
    USER_TOKEN("encryption_token_preference")
}