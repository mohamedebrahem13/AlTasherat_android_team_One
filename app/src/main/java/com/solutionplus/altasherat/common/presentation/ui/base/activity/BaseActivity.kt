package com.solutionplus.altasherat.common.presentation.ui.base.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.android.extentions.bindView
import com.solutionplus.altasherat.android.extentions.showShortToast
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.ui.base.IExceptionHandling

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity(), IExceptionHandling {

    private lateinit var _binding: Binding
    protected val binding: Binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = bindView()

        viewInit()
        onActivityReady(savedInstanceState)
    }

    abstract fun viewInit()
    abstract fun onActivityReady(savedInstanceState: Bundle?)

    override fun handleException(
        exception: AlTasheratException,
        handleValidationErrors: (Map<String, String>) -> Unit
    ) {
        when (exception) {
            is AlTasheratException.Client.Unauthorized -> {
                showShortToast(exception.message ?: "Unauthorized Access")
            }

            is AlTasheratException.Client.Unhandled -> {
                showShortToast(
                    exception.message
                        ?: "Unhandled client error with code: ${exception.httpErrorCode}"
                )
            }

            is AlTasheratException.Local.IOOperation -> {
                showShortToast(exception.message ?: "IO Operation failed")
            }

            is AlTasheratException.Local.RequestValidation -> {
                val errors = exception.errors.mapValues { getString(it.value) }
                handleValidationErrors(errors)
            }

            is AlTasheratException.Network.Retrial -> {
                showShortToast(exception.message ?: "Retrial")
            }

            is AlTasheratException.Network.Unhandled -> {
                showShortToast(exception.message ?: "Unhandled network error")
            }

            is AlTasheratException.Client.ResponseValidation -> {
                handleValidationErrors(exception.errors)
            }

            is AlTasheratException.Server.InternalServerError -> {
                showShortToast(
                    exception.message
                        ?: "Internal server error with code: ${exception.httpErrorCode}"
                )
            }

            is AlTasheratException.Unknown -> {
                showShortToast(exception.message ?: "Unknown error")
            }
        }
    }
}