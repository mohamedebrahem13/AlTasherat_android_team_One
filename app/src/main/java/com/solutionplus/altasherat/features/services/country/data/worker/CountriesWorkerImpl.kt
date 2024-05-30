package com.solutionplus.altasherat.features.services.country.data.worker

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.solutionplus.altasherat.features.services.country.domain.worker.CountriesWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CountriesWorkerImpl @Inject constructor(private val context: Context) {

    suspend fun startCountriesWorker(language: String): Flow<WorkInfo> = flow {
        val inputData = workDataOf(CountriesWorker.KEY_LANGUAGE to language)

        val countriesWorker = OneTimeWorkRequestBuilder<CountriesWorker>()
            .setInputData(inputData)
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