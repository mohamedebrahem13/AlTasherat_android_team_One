package com.solutionplus.altasherat.common.data.repository.local.encryption

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IKeyStoreService
import com.solutionplus.altasherat.common.domain.repository.local.encryption.ISecretKeyAliasEnum
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class EncryptionService(
    private val keyStoreService: IKeyStoreService
) : IEncryptionService {

    private val cipher = Cipher.getInstance(keyStoreService.transformation)

    override fun encrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray {
        return try {
            cipher.init(Cipher.ENCRYPT_MODE, keyStoreService.getSecretKey(keyAlias.keyAlias))
            val encryptedData = cipher.doFinal(data)
            val iv = cipher.iv
            iv + encryptedData
        } catch (exception: Exception) {
            throw AlTasheratException.Local.IOOperation(
                messageRes = R.string.encryption_error,
                message = exception.message
            )
        }
    }

    override fun decrypt(keyAlias: ISecretKeyAliasEnum, data: ByteArray): ByteArray {
        return try {
            val iv = data.copyOfRange(0, cipher.blockSize)
            val encryptedData = data.copyOfRange(cipher.blockSize, data.size)
            cipher.init(
                Cipher.DECRYPT_MODE,
                keyStoreService.getSecretKey(keyAlias.keyAlias),
                IvParameterSpec(iv)
            )
            cipher.doFinal(encryptedData)
        } catch (exception: Exception) {
            throw AlTasheratException.Local.IOOperation(
                messageRes = R.string.decryption_error,
                message = exception.message
            )
        }
    }

    override fun isSecretKeyExist(keyAlias: ISecretKeyAliasEnum): Boolean {
        return keyStoreService.isSecretKeyExist(keyAlias.keyAlias)
    }

    override fun deleteSecretKey(keyAlias: ISecretKeyAliasEnum) {
        keyStoreService.deleteSecretKey(keyAlias.keyAlias)
    }
}