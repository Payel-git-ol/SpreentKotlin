package com.example.kotlintest.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.kotlintest.ui.models.BottomNavItem
import com.example.kotlintest.ui.validate.lists.City
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.runtime.produceState
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.kotlintest.api.getWeather
import com.example.kotlintest.api.models.WeatherResponse
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavController) {
    var selectedCity by remember { mutableStateOf("Минск") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StudyAppHeader(title = "Kotlin Weather")
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


@Composable
fun StudyAppHeader(
    title: String = "",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
fun ImageWeather(city: String) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val weatherState = produceState<WeatherResponse?>(initialValue = null, city) {
        value = getWeather(city)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (currentHour in 6..18) {
            Icon(Icons.Default.WbSunny, contentDescription = "Sun", modifier = Modifier.size(120.dp))
        } else {
            Icon(Icons.Default.DarkMode, contentDescription = "Moon", modifier = Modifier.size(120.dp))
        }

        Spacer(Modifier.height(10.dp))

        weatherState.value?.let { weather ->
            Text("${weather.main.temp}°C", style = TextStyle(fontSize = 30.sp))
            Text(weather.weather[0].description)
        } ?: Text("Загрузка...")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropdown(
    text: String,
    selectedCity: String,
    onCitySelected: (String) -> Unit
) {
    val cities = City
    var expanded by remember { mutableStateOf(false) }
    val weatherState = produceState<WeatherResponse?>(initialValue = null, selectedCity) {
        value = getWeather(selectedCity)
    }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCity,
                onValueChange = {},
                readOnly = true,
                label = { Text(text) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city) },
                        onClick = {
                            onCitySelected(city)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))


    }
}

@Composable
fun AdditionallyInfo(
    city: String,
    styleText: TextStyle
) {
    val weatherState = produceState<WeatherResponse?>(initialValue = null, city) {
        value = getWeather(city)
    }
    val connection = object : NestedScrollConnection {}
    val dispatcher = NestedScrollDispatcher()
        weatherState.value?.let { weather ->
            LazyColumn (
                modifier = Modifier
                    .nestedScroll(connection = connection, dispatcher = dispatcher)
            ) {
                item { CardItem("Ощущается как: ${weather.main.feels_like}°C", styleText) }
                item { CardItem("Влажность: ${weather.main.humidity}%", styleText) }
                item { CardItem("Облачность: ${weather.clouds.all}%", styleText) }
                item { CardItem("Ветер: ${weather.wind.speed} м/с, направление ${weather.wind.deg}°", styleText) }
                item { CardItem("Давление: ${weather.main.pressure} гПа", styleText) }
                item { CardItem("Минимум: ${weather.main.temp_min}°C / Максимум: ${weather.main.temp_max}°C", styleText) }
            }
        }
}

@Composable
fun CardItem(text: String, style: TextStyle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(text, style = style)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Это профиль")
    }
}