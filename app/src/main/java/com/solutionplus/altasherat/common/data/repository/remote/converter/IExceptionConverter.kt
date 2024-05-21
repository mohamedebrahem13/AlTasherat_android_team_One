package com.solutionplus.altasherat.common.data.repository.remote.converter

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import okhttp3.ResponseBody

interface IExceptionConverter {
    fun convertNetworkExceptions(throwable: Throwable): AlTasheratException
    fun convertResponseExceptions(code: Int, errorBody: ResponseBody?): AlTasheratException
}