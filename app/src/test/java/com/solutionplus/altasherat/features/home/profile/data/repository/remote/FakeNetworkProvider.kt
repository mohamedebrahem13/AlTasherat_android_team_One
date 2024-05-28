package com.solutionplus.altasherat.features.home.profile.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.home.profile.data.models.LogoutResponse
import okhttp3.RequestBody
import java.lang.reflect.Type

 class FakeNetworkProvider : INetworkProvider {
     override suspend fun <ResponseBody, RequestBody> post(
         responseWrappedModel: Type,
         pathUrl: String,
         headers: Map<String, Any>?,
         queryParams: Map<String, Any>?,
         requestBody: RequestBody?
     ): ResponseBody {
         TODO("Not yet implemented")
     }

     override suspend fun <ResponseBody> delete(
         responseWrappedModel: Type,
         pathUrl: String,
         headers: Map<String, Any>?,
         queryParams: Map<String, Any>?
     ): ResponseBody {
         @Suppress("UNCHECKED_CAST")
         return LogoutResponse(message = "Logout successful") as ResponseBody
     }

     override suspend fun <ResponseBody, RequestBody> put(
         responseWrappedModel: Type,
         pathUrl: String,
         headers: Map<String, Any>?,
         queryParams: Map<String, Any>?,
         requestBody: RequestBody?
     ): ResponseBody {
         TODO("Not yet implemented")
     }

     override suspend fun <ResponseBody> get(
         responseWrappedModel: Type,
         pathUrl: String,
         headers: Map<String, Any>?,
         queryParams: Map<String, Any>?
     ): ResponseBody {
         TODO("Not yet implemented")
     }

 }