package com.solutionplus.altasherat.common.data.repository.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface AlTasheratApiServices {
    @POST("{path}")
    @JvmSuppressWildcards
    suspend fun post(
        @Path("path") pathUrl: String, @QueryMap queryParams: Map<String, Any>,
        @HeaderMap headers: Map<String, Any>, @Body requestBody: Any,
    ): Response<ResponseBody>

    @GET("{path}")
    @JvmSuppressWildcards
    suspend fun get(
        @Path("path") pathUrl: String, @QueryMap queryParams: Map<String, Any>,
        @HeaderMap headers: Map<String, Any>,
    ): Response<ResponseBody>

    @PUT("{path}")
    @JvmSuppressWildcards
    suspend fun put(
        @Path("path") pathUrl: String, @QueryMap queryParams: Map<String, Any>,
        @HeaderMap headers: Map<String, Any>, @Body requestBody: Any,
    ): Response<ResponseBody>

    @DELETE("{path}")
    @JvmSuppressWildcards
    suspend fun delete(
        @Path("path") pathUrl: String, @QueryMap queryParams: Map<String, Any>,
        @HeaderMap headers: Map<String, Any>, @Body requestBody: Any,
    ): Response<ResponseBody>
}