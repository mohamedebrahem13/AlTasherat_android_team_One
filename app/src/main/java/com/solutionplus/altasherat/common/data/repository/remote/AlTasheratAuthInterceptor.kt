package com.solutionplus.altasherat.common.data.repository.remote

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.features.services.token.domain.interactor.GetCachedTokenUC
import com.solutionplus.altasherat.features.services.language.domain.interactor.GetCachedLanguageUC
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AlTasheratAuthInterceptor(
    private val getCachedTokenUC: GetCachedTokenUC,
    private val getCachedLanguageUC: GetCachedLanguageUC
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder = original.newBuilder().apply {
            addHeader(ACCEPT, APP_JSON)
            addHeader(CONTENT_TYPE, APP_JSON)
        }

        val isAuthRequired = original.headers[AUTH_REQUIRED] == "true"
        requestBuilder.removeHeader(AUTH_REQUIRED)

        if (isAuthRequired) {
            val result = runBlocking { getCachedTokenUC.invoke().drop(1).first() }
            if (result is Resource.Success) {
                requestBuilder.addHeader(AUTH, "Bearer ${result.model.decodeToString()}")
            }
        }

        val result = runBlocking { getCachedLanguageUC.invoke().drop(1).first() }
        if (result is Resource.Success) {
            requestBuilder.addHeader(LOCALE, result.model)
        }

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val ACCEPT = "Accept"
        private const val CONTENT_TYPE = "Content-Type"
        private const val APP_JSON = "application/json"
        private const val AUTH_REQUIRED = "Authorization-Required"
        private const val AUTH = "Authorization"
        private const val LOCALE = "X-locale"
    }
}