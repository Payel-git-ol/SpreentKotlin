package com.example.kotlintest.ui.validate

fun validatePassword(password: String): Boolean {
    val hasMinLength = password.length >= 6
    val hasDigit = password.any { it.isDigit() }
    return hasMinLength && hasDigit
}