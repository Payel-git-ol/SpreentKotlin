package com.example.kotlintest.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StudyAppHeader(title = "Kotlin Weather")
        Spacer(Modifier.height(70.dp))
        CityDropdown(text = "Выберите город")
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
    val correntTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
            if (correntTime in 6..18) {
                Icon(
                    Icons.Default.WbSunny,
                    contentDescription = "Sun",
                    modifier = Modifier.size(120.dp)
                )
            } else {
                Icon(
                    Icons.Default.DarkMode,
                    contentDescription = "Moon",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
            if (city == "Барановичи") {
                Text(
                    text = "-5°C",
                    style = TextStyle(
                        fontSize = 30.sp
                    )
                )
            }
    }
    Spacer(Modifier.height(70.dp))
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun CityDropdown(text: String) {
    val cities = City
    var expanded by remember { mutableStateOf(false) }
    var selectCity by remember { mutableStateOf(cities[0]) }
    ImageWeather(city = selectCity)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectCity,
            onValueChange = {

            },
            readOnly = true,
            label = {
                Text(
                    text = text
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded)},
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false}
        ) {
            cities.forEach { city ->
                DropdownMenuItem(
                    text = { Text(city) },
                    onClick = {
                        selectCity = city
                        expanded = false
                    }
                )
            }
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