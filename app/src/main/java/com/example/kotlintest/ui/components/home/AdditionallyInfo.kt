package com.example.kotlintest.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintest.api.getWeather
import com.example.kotlintest.api.models.WeatherResponse

@Composable
fun AdditionallyInfo(
    city: String,
    styleText: TextStyle
) {
    val weatherState = produceState<WeatherResponse?>(initialValue = null, city) {
        value = getWeather(city)
    }

    weatherState.value?.let { weather ->
        LazyColumn {
            item {
                WeatherInfoCard(
                    title = "Ощущается как",
                    value = "${weather.main.feels_like}°C",
                    icon = Icons.Default.Thermostat,
                    styleText = styleText
                )
            }
            item {
                WeatherInfoCard(
                    title = "Влажность",
                    value = "${weather.main.humidity}%",
                    icon = Icons.Default.WaterDrop,
                    styleText = styleText
                )
            }
            item {
                WeatherInfoCard(
                    title = "Облачность",
                    value = "${weather.clouds.all}%",
                    icon = Icons.Default.Cloud,
                    styleText = styleText
                )
            }
            item {
                WeatherInfoCard(
                    title = "Ветер",
                    value = "${weather.wind.speed} м/с",
                    subtitle = "Направление ${weather.wind.deg}°",
                    icon = Icons.Default.Air,
                    styleText = styleText
                )
            }
            item {
                WeatherInfoCard(
                    title = "Давление",
                    value = "${weather.main.pressure} гПа",
                    icon = Icons.Default.Speed,
                    styleText = styleText
                )
            }
            item {
                TemperatureRangeCard(
                    minTemp = weather.main.temp_min,
                    maxTemp = weather.main.temp_max,
                    styleText = styleText
                )
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun WeatherInfoCard(
    title: String,
    value: String,
    icon: ImageVector,
    styleText: TextStyle,
    subtitle: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Убираем тень
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = styleText.copy(
                            fontSize = 14.sp,
                        )
                    )
                    subtitle?.let {
                        Text(
                            text = it,
                            style = styleText.copy(
                                fontSize = 12.sp,
                            )
                        )
                    }
                }
            }
            Text(
                text = value,
                style = styleText.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            )
        }
    }
}

@Composable
fun TemperatureRangeCard(
    minTemp: Double,
    maxTemp: Double,
    styleText: TextStyle
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Убираем тень
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = "Температура",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Температура",
                    style = styleText.copy(
                        fontSize = 14.sp,
                    )
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Мин",
                        style = styleText.copy(
                            fontSize = 12.sp,
                        )
                    )
                    Text(
                        text = "${minTemp}°C",
                        style = styleText.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Макс",
                        style = styleText.copy(
                            fontSize = 12.sp,
                        )
                    )
                    Text(
                        text = "${maxTemp}°C",
                        style = styleText.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                }
            }
        }
    }
}