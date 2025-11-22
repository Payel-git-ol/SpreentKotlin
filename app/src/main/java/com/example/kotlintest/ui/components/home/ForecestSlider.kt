package com.example.kotlintest.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintest.api.models.ForecastResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ForecastSlider(forecast: ForecastResponse) {

    val grouped = forecast.list.groupBy {
        SimpleDateFormat("dd MMMM", Locale("ru"))
            .format(Date(it.dt * 1000))
    }

    val colorText = MaterialTheme.colorScheme.onBackground

    LazyRow {
        grouped.forEach { (date, items) ->
            val dayTemp = items.first().main.temp
            val desc = items.first().weather.first().description

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(6.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        date,
                        color = colorText
                    )
                    Text(
                        "${dayTemp}°C",
                        color = colorText
                    )
                    Text(
                        desc,
                        color = colorText
                    )
                }
            }
        }
    }
}
