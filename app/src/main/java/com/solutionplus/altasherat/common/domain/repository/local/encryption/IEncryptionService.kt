package com.solutionplus.altasherat.common.domain.repository.local.encryption

interface IEncryptionService {
    fun encrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray
    fun decrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray
    fun isSecretKeyExist(keyAlias: ISecretKeyAliasEnum): Boolean
    fun deleteSecretKey(keyAlias: ISecretKeyAliasEnum)
}