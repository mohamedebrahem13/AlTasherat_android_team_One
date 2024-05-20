package com.solutionplus.altasherat.features.splash.domain.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class CountriesWorker  @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val getAndSaveCountriesUseCase: GetAndSaveCountriesUseCase
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                // Invoke the use case and collect the result
                getAndSaveCountriesUseCase.invoke(params = "en", scope = this) { resource ->
                    // Handle the resource accordingly
                    when (resource) {
                        is Resource.Progress -> {
                            // Handle progress if needed
                        }
                        is Resource.Success-> {
                            // Handle success if needed
                            val successMessage = "Countries saved successfully"
                            val outputData = workDataOf(KEY_SUCCESS_MESSAGE to successMessage)
                            Result.success(outputData)
                        }
                        is Resource.Failure-> {
                            // Handle failure if needed
                            val errorMessage = "Failed to save countries: ${resource.exception.message}"
                            val outputData = workDataOf(KEY_ERROR_MESSAGE to errorMessage)
                            Result.failure(outputData)
                        }
                    }
                }
            }
            Result.success()

            // Return success if the use case completes without exceptions
        } catch (e: Exception) {
            // Handle exceptions and return failure
            val errorMessage = "An error occurred: ${e.message}"
            val outputData = workDataOf(KEY_ERROR_MESSAGE to errorMessage)
            Result.failure(outputData)
        }
    }
    companion object {
        const val WORK_NAME = "CountriesWorker"
        const val KEY_SUCCESS_MESSAGE = "success_message"
        const val KEY_ERROR_MESSAGE = "error_message" // New key for error message
        private val logger = getClassLogger()
    }
}