package com.example.kotlintest.api

import com.example.kotlintest.api.client.client
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun sendUserGet(email: String, password: String): String {
    val response: String = client.get("http://10.0.2.2:8080/api/user") {
        url {
            parameters.append("email", email)
            parameters.append("password", password)
        }
    }.body()
    return response
}
