package com.example.kotlintest.api

import com.example.kotlintest.api.client.client
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import com.example.kotlintest.BuildConfig

suspend fun getWeather(city: String): String {

    val apiKey = BuildConfig.OPENWEATHER_API_KEY

    val response = client.get("https://api.openweathermap.org/data/2.5/weather") {
        parameter("q", city)
        parameter("appid", apiKey)
        parameter("units", "metric")
        parameter("lang", "ru")
    }

    return response.bodyAsText()
}
