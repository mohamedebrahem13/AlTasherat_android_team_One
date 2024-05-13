package com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate

import android.content.Context
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

interface ExceptionHandler {
    fun handleException(exception: AlTasheratException, context: Context)
}