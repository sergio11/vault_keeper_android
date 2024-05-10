package com.dreamsoftware.runtracker.data.network.interceptor

import com.dreamsoftware.runtracker.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OpenWeatherInterceptor: Interceptor {

    private val openWeatherApiKey by lazy {
        BuildConfig.OPEN_WEATHER_API_KEY
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request()
        val url = currentRequest.url.newBuilder()
            .addQueryParameter(APP_ID_QUERY_PARAM_NAME, openWeatherApiKey)
            .build()
        val newRequest = currentRequest
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val APP_ID_QUERY_PARAM_NAME = "APPID"
    }
}