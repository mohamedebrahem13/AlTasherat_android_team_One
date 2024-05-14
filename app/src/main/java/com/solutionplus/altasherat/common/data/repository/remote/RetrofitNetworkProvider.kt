package com.solutionplus.altasherat.common.data.repository.remote

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.reflect.Type
import java.net.HttpURLConnection

class RetrofitNetworkProvider(private val apiServices: AlTasheratApiServices) : INetworkProvider {
    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.post(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return wrap(response, responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.delete(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return wrap(response, responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.put(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return wrap(response, responseWrappedModel)
    }

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val response = apiServices.get(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
        return wrap(response, responseWrappedModel)
    }

    private fun <Model> wrap(response: Response<ResponseBody>, responseWrappedModel: Type): Model {
        return if (response.isSuccessful) {
            val responseBody = response.body() ?: throw AlTasheratException.Client.Unhandled(
                httpErrorCode = response.code(),
                message = "Response body is empty"
            )
            Gson().fromJson(responseBody.string(), responseWrappedModel)
        } else {
            when (response.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> throw AlTasheratException.Client.Unauthorized
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw AlTasheratException.Server.InternalServerError(
                    httpErrorCode = response.code(),
                    message = response.errorBody().toString()
                )

                else -> throw AlTasheratException.Client.Unhandled(
                    httpErrorCode = response.code(),
                    message = response.errorBody().toString()
                )
            }
        }
    }
}