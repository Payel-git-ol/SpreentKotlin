package com.example.kotlintest.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)

@Serializable
data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int? = null,
    val pop: Double? = null,
    val sys: ForecastSys? = null,
    val dt_txt: String
)

@Serializable
data class ForecastSys(val pod: String) // "d" или "n"

@Serializable
data class City(
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)
