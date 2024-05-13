package com.solutionplus.altasherat.android.helpers.logging

import com.solutionplus.altasherat.BuildConfig
import com.solutionplus.altasherat.android.helpers.logging.writers.LogcatWriter

object LoggerProvider {
    fun provideLogger(tagKey: String = "AlTasherat") {
        LoggerFactory.setLogWriter(LogcatWriter(tagKey, BuildConfig.DEBUG))
    }
}