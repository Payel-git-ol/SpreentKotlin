package com.example.kotlintest.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kotlintest.service.CityManager
import com.example.kotlintest.ui.components.header.StudyAppHeader
import com.example.kotlintest.ui.validate.lists.Country
import com.example.kotlintest.ui.validate.lists.getCitiesForCountry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController) {
    var selectedCity by remember { mutableStateOf("Минск") }
    var selectedCountry by remember { mutableStateOf("Беларусь") }
    val context = LocalContext.current

    val cities = getCitiesForCountry(selectedCountry)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StudyAppHeader("Profile")
        Spacer(Modifier.height(30.dp))

        ImageProfile()
        Spacer(Modifier.height(25.dp))

        EmailUser(email = "testemail@gmail.com")
        Spacer(Modifier.height(25.dp))

        LaunchedEffect(Unit) {
            CityManager.getDefaults(context).collect { (city, country) ->
                selectedCity = city
                selectedCountry = country
            }
        }

        DropdownCountryDefault(
            country = Country,
            selectiveCountry = selectedCountry,
            onCountrySelected = { newCountry ->
                selectedCountry = newCountry
                // при смене страны сбрасываем город на первый из списка
                selectedCity = getCitiesForCountry(newCountry).first()
                CoroutineScope(Dispatchers.IO).launch {
                    CityManager.saveDefaults(context, selectedCity, selectedCountry)
                }
            }
        )

        DropdownCityDefault(
            cities = getCitiesForCountry(selectedCountry),
            selectiveCity = selectedCity,
            onCitySelected = { newCity ->
                selectedCity = newCity
                CoroutineScope(Dispatchers.IO).launch {
                    CityManager.saveDefaults(context, selectedCity, selectedCountry)
                }
            }
        )
    }
}



@Composable
fun ImageProfile() {
    Image(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "AccountCircle",
        modifier = Modifier.size(120.dp)
    )
}

@Composable
fun EmailUser(email: String) {
    Text(
        text = email,
        style = TextStyle(fontSize = 25.sp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCountryDefault(
    country: List<String>,
    selectiveCountry: String,
    onCountrySelected: (String) -> Unit
) {
    var expended by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expended,
        onExpandedChange = { expended = !expended }
    ) {
        TextField(
            value = selectiveCountry,
            onValueChange = { },
            readOnly = true,
            label = { Text("Страна по умолчанию") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false }
        ) {
            country.forEach { city ->
                DropdownMenuItem(
                    text = { Text(city) },
                    onClick = {
                        onCountrySelected(city)
                        expended = false
                    })

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCityDefault(
    cities: List<String>,
    selectiveCity: String,
    onCitySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectiveCity,
            onValueChange = { },
            readOnly = true,
            label = { Text("Город по умолчанию") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
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
                    })

            }
        }
    }
}