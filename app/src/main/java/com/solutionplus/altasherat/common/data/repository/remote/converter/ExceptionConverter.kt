package com.solutionplus.altasherat.common.data.repository.remote.converter

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
import com.solutionplus.altasherat.common.data.models.ResponseErrorBody
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import okhttp3.ResponseBody
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionConverter : IExceptionConverter {
    override fun convertNetworkExceptions(throwable: Throwable): AlTasheratException {
        return when (throwable) {
            is SocketTimeoutException, is UnknownHostException, is IOException -> AlTasheratException.Network.Retrial(
                messageRes = R.string.network_connection_error,
                message = "Retrial network error."
            )

            else -> AlTasheratException.Network.Unhandled(
                messageRes = R.string.unknown_network_error,
                message = "NetworkException Unhandled error."
            )
        }
    }

    override fun convertResponseExceptions(
        code: Int, errorBody: ResponseBody?
    ): AlTasheratException {
        return when (code) {
            401 -> AlTasheratException.Client.Unauthorized

            in 400..499 -> handleClientException(code, errorBody)

            in 500..599 -> AlTasheratException.Server.InternalServerError(
                httpErrorCode = code,
                message = errorBody?.string()
            )

            else -> AlTasheratException.Client.Unhandled(
                httpErrorCode = code,
                message = errorBody?.string()
            )
        }
    }

    private fun handleClientException(code: Int, errorBody: ResponseBody?): AlTasheratException {
        return if (errorBody == null) {
            AlTasheratException.Client.Unhandled(
                httpErrorCode = code,
                message = "There is no error body for this code."
            )
        } else {
            try {
                handleValidationException(code, errorBody)
            } catch (e: Exception) {
                AlTasheratException.Client.Unhandled(
                    httpErrorCode = code,
                    message = e.message
                )
            }
        }
    }

    private fun handleValidationException(code: Int, errorBody: ResponseBody): AlTasheratException {
        val responseErrorBody =
            errorBody.string().trimIndent()
                .getModelFromJSON<ResponseErrorBody>(ResponseErrorBody::class.java)

        val errorsMap =
            responseErrorBody.errors.map { it.key to it.value.joinToString(", ") }.toMap()

        val message = responseErrorBody.message

        return when (code) {
            422 -> AlTasheratException.Client.ResponseValidation(
                errors = errorsMap,
                message = message
            )

            else -> AlTasheratException.Client.Unhandled(
                httpErrorCode = code,
                message = message
            )
        }
    }
}