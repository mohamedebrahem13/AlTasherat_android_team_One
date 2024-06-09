package com.solutionplus.altasherat.common.presentation.ui.base

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction

interface IExceptionHandling {
    fun handleException(
        exception: AlTasheratException,
        action: ViewAction? = null,
        handleValidationErrors: (Map<String, String>) -> Unit = {}
    )
}