package com.solutionplus.altasherat.features.splash.data.repository.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import java.lang.reflect.Type

class FakeNetworkProvider : INetworkProvider {
    private val responseMap = mutableMapOf<String, Any>()

    fun <T> setResponse(endpoint: String, response: T) {
        responseMap[endpoint] = response!!
    }

    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        return castResponse(responseWrappedModel, pathUrl)
    }

    override suspend fun <ResponseBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
    ): ResponseBody {
        return castResponse(responseWrappedModel, pathUrl)
    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        return castResponse(responseWrappedModel, pathUrl)
    }

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        return castResponse(responseWrappedModel, pathUrl)
    }

    private fun <ResponseBody> castResponse(type: Type, pathUrl: String): ResponseBody {
        val responseJson = Gson().toJson(responseMap[pathUrl])
        return Gson().fromJson(responseJson, TypeToken.get(type).type)
    }
}