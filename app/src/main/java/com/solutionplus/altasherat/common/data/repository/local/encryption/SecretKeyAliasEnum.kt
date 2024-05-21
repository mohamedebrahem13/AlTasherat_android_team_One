package com.solutionplus.altasherat.common.data.repository.local.encryption

import com.solutionplus.altasherat.common.domain.repository.local.encryption.ISecretKeyAliasEnum

enum class SecretKeyAliasEnum(override val keyAlias: String) : ISecretKeyAliasEnum {
    USER_SECRET_KEY("user_secret_key"),
    USER_TOKEN_SECRET_KEY("user_token_secret_key")
}