package com.example.kotlintest.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

val client = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun sendUserGet(email: String, password: String): String {
    val response: String = client.get("http://10.0.2.2:8080/api/user") {
        url {
            parameters.append("email", email)
            parameters.append("password", password)
        }
    }.body()
    return response
}
