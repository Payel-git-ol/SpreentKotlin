package com.example.kotlintest.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.kotlintest.api.getForecast
import com.example.kotlintest.api.models.ForecastResponse
import com.example.kotlintest.service.CityManager
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var forecast by remember { mutableStateOf<ForecastResponse?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var selectedCity by remember { mutableStateOf("Минск") }
    var selectedCountry by remember { mutableStateOf("BY") }

    LaunchedEffect(Unit) {
        CityManager.getDefaults(context).collect { (city, country) ->
            selectedCity = city
            selectedCountry = country
        }
    }

    LaunchedEffect(selectedCity, selectedCountry) {
        forecast = try { getForecast(selectedCity, selectedCountry) } catch (e: Exception) { null }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            scope.launch {
                isRefreshing = true
                forecast = try { getForecast(selectedCity, selectedCountry) } catch (e: Exception) { null }
                isRefreshing = false
            }
        }
    ) {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        StudyAppHeader(title = "Native Weather")
                        Spacer(Modifier.height(30.dp))

                        ImageWeather(city = selectedCity, countryCode = selectedCountry)


                        Spacer(Modifier.height(30.dp))
                        forecast?.let {
                            ForecastSlider(forecast = it)
                        }
                        Spacer(Modifier.height(30.dp))
                        CityDropdown(
                            text = "Выберите город",
                            selectedCity = selectedCity,
                            selectedCountry = selectedCountry,
                            onCitySelected = { city ->
                                selectedCity = city
                            }
                        )

                    }
                }
            }

            item {
                AdditionallyInfo(
                    city = selectedCity,
                    countryCode = selectedCountry,
                    styleText = TextStyle(fontSize = 20.sp)
                )

            }
        }
    }




}
