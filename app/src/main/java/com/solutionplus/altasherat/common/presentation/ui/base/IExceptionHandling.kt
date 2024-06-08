package com.solutionplus.altasherat.common.presentation.ui.base

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

interface IExceptionHandling {
    fun handleException(
        exception: AlTasheratException,
        handleValidationErrors: (Map<String, String>) -> Unit = {}
    )
}