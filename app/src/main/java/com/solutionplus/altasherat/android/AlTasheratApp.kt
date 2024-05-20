package com.solutionplus.altasherat.android

import android.app.Application
import androidx.work.Configuration
import com.solutionplus.altasherat.android.helpers.logging.LoggerProvider
import com.solutionplus.altasherat.features.splash.domain.worker.CountriesWorker
import com.solutionplus.altasherat.features.splash.domain.worker.CountriesWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AlTasheratApp : Application(), Configuration.Provider {
    @Inject
    lateinit var hiltWorkerFactory: CountriesWorkerFactory

    override fun onCreate() {
        super.onCreate()
        LoggerProvider.provideLogger()
    }

    override val workManagerConfiguration: Configuration
        get() =  Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory) // Set your custom worker factory
            .build()

}