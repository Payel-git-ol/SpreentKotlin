package com.example.kotlintest.api.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val name: String,
    val cod: Int
)

@Serializable
data class Coord(val lon: Double, val lat: Double)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Wind(val speed: Double, val deg: Int, val gust: Double? = null)

@Serializable
data class Clouds(val all: Int)

@Serializable
data class Sys(val country: String, val sunrise: Long, val sunset: Long)
