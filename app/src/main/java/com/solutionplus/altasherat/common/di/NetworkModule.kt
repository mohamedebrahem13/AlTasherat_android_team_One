package com.solutionplus.altasherat.common.di

import com.solutionplus.altasherat.BuildConfig
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.remote.AlTasheratApiServices
import com.solutionplus.altasherat.common.data.repository.remote.RetrofitNetworkProvider
import com.solutionplus.altasherat.common.data.repository.remote.call_factory.AlTasheratCallAdapterFactory
import com.solutionplus.altasherat.common.data.repository.remote.converter.ExceptionConverter
import com.solutionplus.altasherat.common.data.repository.remote.converter.IExceptionConverter
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofitNetwork(altasheratApiService: AlTasheratApiServices): INetworkProvider {
        return RetrofitNetworkProvider(altasheratApiService)
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory,
        alTasheratCallAdapterFactory: AlTasheratCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl("https://intern.api.altashirat.solutionplus.net/api/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(alTasheratCallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAlTasheratApiService(retrofit: Retrofit): AlTasheratApiServices =
        retrofit.create(AlTasheratApiServices::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().apply {
            connectTimeout(30L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            connectionPool(
                ConnectionPool(30L.toInt(), 500000, TimeUnit.MILLISECONDS)
            )
            readTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideCallAdapter(exceptionConverter: IExceptionConverter): AlTasheratCallAdapterFactory {
        return AlTasheratCallAdapterFactory.create(exceptionConverter)
    }

    @Provides
    @Singleton
    fun provideExceptionConverter(): IExceptionConverter {
        return ExceptionConverter()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor { message ->
            if (BuildConfig.DEBUG) logger.warning(message)
        }.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    private val logger = getClassLogger()
}