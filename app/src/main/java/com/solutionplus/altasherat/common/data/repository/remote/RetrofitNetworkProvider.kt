package com.solutionplus.altasherat.common.data.repository.remote

import com.solutionplus.altasherat.android.extentions.createPartFromFile
import com.solutionplus.altasherat.android.extentions.createPartFromFilesList
import com.solutionplus.altasherat.android.extentions.createPartFromString
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.reflect.Type

class RetrofitNetworkProvider(private val apiServices: AlTasheratApiServices) : INetworkProvider {
    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.post(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return response.string().getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val response = apiServices.delete(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
        return response.string().getModelFromJSON(responseWrappedModel)
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
        return response.string().getModelFromJSON(responseWrappedModel)
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
        return response.string().getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody> postWithFile(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: Map<String, Any>?,
        files: Map<String, List<File>>?
    ): ResponseBody {
        val multipartList = mutableListOf<MultipartBody.Part>().apply {
            if (files.isNullOrEmpty()) return@apply

            files.forEach {
                if (it.value.count() == 1)
                    add(it.value.first().createPartFromFile(it.key))
                else
                    addAll(it.value.createPartFromFilesList(it.key))
            }
        }

        val bodyMap = hashMapOf<String, RequestBody>().apply {
            requestBody?.forEach { (key, body) ->
                put(key, body.toString().createPartFromString())
            }
        }

        val response = apiServices.postWithFile(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = bodyMap, file = multipartList
        )
        return response.string().getModelFromJSON(responseWrappedModel)

    }
}





