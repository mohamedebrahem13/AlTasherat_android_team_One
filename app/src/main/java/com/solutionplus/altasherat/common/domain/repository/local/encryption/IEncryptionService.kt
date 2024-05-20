package com.solutionplus.altasherat.common.domain.repository.local.encryption

interface IEncryptionService {
    fun encrypt(keyAlias: String, data: ByteArray): ByteArray
    fun decrypt(keyAlias: String, data: ByteArray): ByteArray
}