package com.solutionplus.altasherat.common.presentation.ui

import android.content.Context
import android.widget.Toast
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

class DefaultResourceErrorHandlerDelegate : ExceptionHandler {

    override fun handleException(exception: AlTasheratException, context: Context) {
        val errorMessage = when (exception) {
            is AlTasheratException.Network.Retrial -> "Network Retrial: ${exception.message}"
            is AlTasheratException.Network.Unhandled -> "Network Unhandled: ${exception.message}"
            is AlTasheratException.Client.Unauthorized -> "Unauthorized Access"
            is AlTasheratException.Client.ResponseValidation -> "Client Response Validation Error: ${exception.message}"
            is AlTasheratException.Client.Unhandled -> "Client Unhandled: ${exception.message}"
            is AlTasheratException.Server.InternalServerError -> "Internal Server Error: ${exception.message}"
            is AlTasheratException.Local.RequestValidation -> "Local Request Validation Error: ${exception.message}"
            is AlTasheratException.Local.IOOperation -> "Local IO Operation Error: ${exception.message}"
            is AlTasheratException.Unknown -> "Unknown Error: ${exception.message}"
        }
        showToast(context, errorMessage)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
