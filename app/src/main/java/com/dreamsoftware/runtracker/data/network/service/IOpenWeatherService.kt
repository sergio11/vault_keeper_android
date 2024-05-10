package com.dreamsoftware.runtracker.data.network.service

import com.dreamsoftware.runtracker.data.network.dto.WeatherResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface IOpenWeatherService {

    /**
     * Get Current Weather Data
     */
    @GET("weather")
    fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): WeatherResponseDTO
}