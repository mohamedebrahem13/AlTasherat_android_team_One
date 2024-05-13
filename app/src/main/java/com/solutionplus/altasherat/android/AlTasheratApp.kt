package com.solutionplus.altasherat.android

import android.app.Application
import com.solutionplus.altasherat.android.helpers.logging.LoggerProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlTasheratApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerProvider.provideLogger()
    }
}