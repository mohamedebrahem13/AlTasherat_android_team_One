package com.solutionplus.altasherat.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.solutionplus.altasherat.android.helpers.logging.LoggerProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlTasheratApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerProvider.provideLogger()
    }
}