package com.solutionplus.altasherat.common.data.models.exception

import androidx.annotation.StringRes
import kotlin.reflect.KClass

sealed class AlTasheratException(message: String?) : Exception(message) {
    sealed class Network(message: String? = null) : AlTasheratException(message) {
        data class Retrial(@StringRes val messageRes: Int, override val message: String?) :
            Network(message)

        data class Unhandled(@StringRes val messageRes: Int, override val message: String?) :
            Network(message)
    }

    sealed class Client(message: String? = null) : AlTasheratException(message) {
        data object Unauthorized : Client(message = "Unauthorized Access.")

        data class ResponseValidation(
            val errors: Map<String, String> = hashMapOf(), override val message: String?
        ) : AlTasheratException(message)

        data class Unhandled(val httpErrorCode: Int, override val message: String?) : Client(
            message = "Unhandled client error with code:${httpErrorCode}, and the failure reason: $message"
        )
    }

    sealed class Server(message: String? = null) : AlTasheratException(message) {
        data class InternalServerError(val httpErrorCode: Int, override val message: String?) :
            Server(message = "Internal server error with code:${httpErrorCode}, and the failure reason: $message")
    }

    sealed class Local(message: String? = null) : AlTasheratException(message) {
        data class RequestValidation(
            val clazz: KClass<*>,
            override val message: String? = null,
            val errors: Map<String, Int> = hashMapOf(),
        ) : Local(StringBuilder("There is missing input for this class: ${clazz.simpleName}").apply {
            message?.let { append(", message: $message") }
        }.toString())

        data class IOOperation(@StringRes val messageRes: Int, override val message: String? = "") :
            Local(message)
    }

    data class Unknown(override val message: String?) : AlTasheratException(message)

    fun isUnauthorized() = this == Client.Unauthorized
}