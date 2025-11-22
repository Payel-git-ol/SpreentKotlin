package com.example.kotlintest.api

import com.example.kotlintest.api.client.client
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.example.kotlintest.BuildConfig
import com.example.kotlintest.api.models.WeatherResponse
import com.example.kotlintest.service.WeatherCache
import io.ktor.client.call.body

suspend fun getWeather(city: String): WeatherResponse {
    WeatherCache.get(city)?.let { return it }

    val apiKey = BuildConfig.OPENWEATHER_API_KEY

    val response = client.get("https://api.openweathermap.org/data/2.5/weather") {
        parameter("q", city)
        parameter("appid", apiKey)
        parameter("units", "metric")
        parameter("lang", "ru")
    }.body<WeatherResponse>()

    return response
}

