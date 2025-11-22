package com.example.kotlintest.service

import com.example.kotlintest.api.models.WeatherResponse

object WeatherCache {
    private val cache = mutableMapOf<String, Pair<Long, WeatherResponse>>()
    private const val CACHE_DURATION = 60 * 60 * 1000

    fun get(city: String): WeatherResponse? {
        val entry = cache[city]
        return if (entry != null && System.currentTimeMillis() - entry.first < CACHE_DURATION) {
            entry.second
        } else null
    }

    fun put(city: String, data: WeatherResponse) {
        cache[city] = System.currentTimeMillis() to data
    }
}