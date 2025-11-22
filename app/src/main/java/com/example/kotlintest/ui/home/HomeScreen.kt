package com.example.kotlintest.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun HomeScreen(navController: NavController) {
    var selectedCity by remember { mutableStateOf("Минск") }

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
