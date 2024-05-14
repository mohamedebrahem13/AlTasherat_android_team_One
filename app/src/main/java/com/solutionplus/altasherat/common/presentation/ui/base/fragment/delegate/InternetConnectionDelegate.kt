package com.solutionplus.altasherat.common.presentation.ui.base.fragment.delegate

import android.content.Context

interface InternetConnectionDelegate {
    fun isInternetAvailable(context: Context): Boolean
}