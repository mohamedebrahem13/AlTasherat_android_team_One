package com.solutionplus.altasherat.common.domain.repository.local.encryption

import javax.crypto.SecretKey

interface IKeyStoreService {
    val transformation: String
    fun getSecretKey(keyAlias: String): SecretKey
    fun isSecretKeyExist(keyAlias: String): Boolean
    fun deleteSecretKey(keyAlias: String)
}