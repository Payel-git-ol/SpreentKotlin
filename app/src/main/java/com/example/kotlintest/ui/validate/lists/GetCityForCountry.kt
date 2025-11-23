package com.example.kotlintest.ui.validate.lists

fun getCitiesForCountry(country: String): List<String> {
    return when (country) {
        "Беларусь" -> CityBy
        "Россия" -> CityRu
        "Украина" -> CityUa
        "Казахстан" -> CityKz
        else -> CityRu
    }
}