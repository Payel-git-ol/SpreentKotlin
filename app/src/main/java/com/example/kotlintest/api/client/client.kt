package com.example.kotlintest.api.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

val client = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
}