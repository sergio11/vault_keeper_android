package com.dreamsoftware.runtracker.data.network.dto

import com.squareup.moshi.Json

data class WeatherResponseDTO (
    @field:Json(name = "weather") val conditions: List<ConditionsInfoDTO>,
    @field:Json(name = "main") val main: MainInfoDTO,
    @field:Json(name = "wind") val wind: WindInfoDTO
)

data class ConditionsInfoDTO(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "icon") val icon: String
)

data class MainInfoDTO(
    @field:Json(name = "temp") val temp: Float,
    @field:Json(name = "humidity") val humidity: Int,
    @field:Json(name = "temp_min") val tempMin: Float,
    @field:Json(name = "temp_max") val tempMax: Float
)

data class WindInfoDTO(
    @field:Json(name = "speed") val speed: Float,
    @field:Json(name = "deg") val deg: Int
)