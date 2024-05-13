package com.solutionplus.altasherat.android.helpers.logging

import com.solutionplus.altasherat.BuildConfig
import com.solutionplus.altasherat.android.helpers.logging.writers.FileWriter

object LoggerProvider {
    fun provideLogger(tagKey: String = "AlTasherat") {
        LoggerFactory.setLogWriter(
            FileWriter(
                folderName = tagKey,
                fileName = "AlTasherat-logFile",
                tagKey = tagKey,
                isDebugEnabled = BuildConfig.DEBUG
            )
        )
    }
}