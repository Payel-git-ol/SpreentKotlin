package com.example.kotlintest.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FilterDrama
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintest.api.getWeather
import com.example.kotlintest.api.models.WeatherResponse
import java.util.Calendar

@Composable
fun ImageWeather(city: String) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val weatherState = produceState<WeatherResponse?>(initialValue = null, city) {
        value = getWeather(city)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(10.dp))
        weatherState.value?.let { weather ->

            if (weather.weather[0].description == "пасмурно" || weather.weather[0].description == "переменная облачность" || weather.weather[0].description == "облачно") {
                Icon(
                    imageVector = Icons.Default.FilterDrama,
                    contentDescription = "Cloud",
                    modifier = Modifier.size(120.dp)
                )
            } else if (weather.weather[0].description == "снег") {
                Icon(
                    imageVector = Icons.Default.AcUnit,
                    contentDescription = "Cloud",
                    modifier = Modifier.size(120.dp)
                )
            } else if (weather.weather[0].description == "солнечно" || weather.weather[0].description == "ясно") {
                if (currentHour in 6..18) {
                    Icon(Icons.Default.WbSunny, contentDescription = "Sun", modifier = Modifier.size(120.dp))
                } else {
                    Icon(Icons.Default.DarkMode, contentDescription = "Moon", modifier = Modifier.size(120.dp))
                }

            } else if (weather.weather[0].description == "дождливо" || weather.weather[0].description == "дождь") {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = "Cloud",
                    modifier = Modifier.size(120.dp)
                )
            } else if (weather.weather[0].description == "туман") {
                Icon(
                    imageVector = Icons.Default.BlurOn,
                    contentDescription = "Cloud",
                    modifier = Modifier.size(120.dp)
                )
            } else if (weather.weather[0].description == "небольшой снег с дождём" || weather.weather[0].description == "снег с дождём") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AcUnit,
                        contentDescription = "Snow",
                        modifier = Modifier.size(80.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = "Rain",
                        modifier = Modifier.size(80.dp)
                    )
                }
                Spacer(Modifier.height(30.dp))
            } else if (weather.weather[0].description == "облачно с прояснениями") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WbCloudy,
                        contentDescription = "Snow",
                        modifier = Modifier.size(80.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Rain",
                        modifier = Modifier.size(80.dp)
                    )
                }
                Spacer(Modifier.height(30.dp))
            }

            Text("${weather.main.temp}°C", style = TextStyle(fontSize = 30.sp))
            Text(weather.weather[0].description)
        } ?: Text("Загрузка...")
    }
}
