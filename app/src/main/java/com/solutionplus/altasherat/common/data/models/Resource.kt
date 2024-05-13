package com.solutionplus.altasherat.common.data.models

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

sealed class Resource<out Model> {
    data class Progress<Model>(val loading: Boolean, val partialData: Model? = null) :
        Resource<Model>()

    data class Success<out Model>(val model: Model) : Resource<Model>()
    data class Failure(val exception: AlTasheratException) : Resource<Nothing>()

    companion object {
        fun <Model> loading(
            loading: Boolean = true, partialData: Model? = null
        ): Resource<Model> = Progress(loading, partialData)

        fun <Model> success(model: Model): Resource<Model> = Success(model)
        fun <Model> failure(exception: AlTasheratException): Resource<Model> = Failure(exception)
    }
}