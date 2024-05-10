package com.dreamsoftware.runtracker.data.network.di

import com.dreamsoftware.runtracker.BuildConfig
import com.dreamsoftware.runtracker.data.network.interceptor.OpenWeatherInterceptor
import com.dreamsoftware.runtracker.data.network.service.IOpenWeatherService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private companion object {
        const val TIMEOUT_IN_MINUTES: Long = 1
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {
        val networkInterceptors = setOf(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.HEADERS)
        })
        val requestInterceptors = setOf(OpenWeatherInterceptor())
        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)
            .readTimeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)
            .writeTimeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
        requestInterceptors.forEach {
            okHttpBuilder.addInterceptor(it)
        }
        networkInterceptors.forEach {
            okHttpBuilder.addNetworkInterceptor(it)
        }
        return okHttpBuilder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converter: Converter.Factory): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .build()

    @Provides
    @Singleton
    fun provideOpenWeatherService(retrofit: Retrofit): IOpenWeatherService =
        retrofit.create(IOpenWeatherService::class.java)
}