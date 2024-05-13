package com.solutionplus.altasherat.android.helpers.logging

import com.solutionplus.altasherat.android.helpers.logging.writers.DummyWriter

object LoggerProvider {
    fun provideLogger() {
        LoggerFactory.setLogWriter(DummyWriter())
    }
}