package com.example.kotlintest.api

import com.example.kotlintest.api.client.client
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.example.kotlintest.BuildConfig
import com.example.kotlintest.api.models.ForecastResponse
import com.example.kotlintest.api.models.WeatherResponse
import com.example.kotlintest.service.WeatherCache
import io.ktor.client.call.body

suspend fun getForecast(city: String, countryCode: String): ForecastResponse {
    val apiKey = BuildConfig.OPENWEATHER_API_KEY

    val response = client.get("https://api.openweathermap.org/data/2.5/forecast") {
        parameter("q", "$city,$countryCode")
        parameter("appid", apiKey)
        parameter("units", "metric")
        parameter("lang", "ru")
    }.body<ForecastResponse>()

    return response
}