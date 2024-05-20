package com.solutionplus.altasherat.features.splash.data.worker

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.solutionplus.altasherat.features.splash.domain.worker.CountriesWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CountriesWorkerImpl @Inject constructor(private val context: Context) {

    suspend fun startCountriesWorker(): Flow<WorkInfo> = flow {
        val countriesWorker = OneTimeWorkRequestBuilder<CountriesWorker>()
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            CountriesWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            countriesWorker
        )

        val workInfo = workManager.getWorkInfoByIdFlow(countriesWorker.id)
        // Filter out null WorkInfo objects before emitting
        emitAll(workInfo.filterNotNull())
    }
}