package com.example.kotlintest.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.kotlintest.ui.components.home.AdditionallyInfo
import com.example.kotlintest.ui.components.home.CityDropdown
import com.example.kotlintest.ui.components.home.ImageWeather
import com.example.kotlintest.ui.components.header.StudyAppHeader
import com.example.kotlintest.ui.components.home.ForecastSlider
import androidx.compose.runtime.LaunchedEffect
import com.example.kotlintest.api.getForecast
import com.example.kotlintest.api.models.ForecastResponse

@Composable
fun HomeScreen(navController: NavController) {
    var selectedCity by remember { mutableStateOf("Минск") }
    var forecast by remember { mutableStateOf<ForecastResponse?>(null) }
    val colorText = MaterialTheme.colorScheme.onBackground

    // Загружаем прогноз при изменении города
    androidx.compose.runtime.LaunchedEffect(selectedCity) {
        forecast = try {
            getForecast(selectedCity)
        } catch (e: Exception) {
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StudyAppHeader(title = "Native Weather")
        Spacer(Modifier.height(30.dp))

        ImageWeather(city = selectedCity)

        Spacer(Modifier.height(30.dp))
        forecast?.let {
            ForecastSlider(forecast = it)
        }
        Spacer(Modifier.height(30.dp))
        CityDropdown(
            text = "Выберите город",
            selectedCity = selectedCity,
            onCitySelected = { selectedCity = it }
        )

        AdditionallyInfo(
            city = selectedCity,
            styleText = TextStyle(fontSize = 20.sp)
        )
    }
}
