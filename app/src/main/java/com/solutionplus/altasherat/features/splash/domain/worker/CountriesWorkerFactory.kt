package com.solutionplus.altasherat.features.splash.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import javax.inject.Inject

class CountriesWorkerFactory @Inject constructor(
    private val getAndSaveCountriesUseCase: GetAndSaveCountriesUseCase
) : WorkerFactory() {


    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            CountriesWorker::class.java.name ->
                CountriesWorker(appContext, workerParameters, getAndSaveCountriesUseCase)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }
}