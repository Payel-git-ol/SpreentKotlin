package com.example.kotlintest.ui.navegation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlintest.ui.home.BottomNavigationBar
import com.example.kotlintest.ui.home.HomeScreen
import com.example.kotlintest.ui.home.ProfileScreen
import com.example.kotlintest.ui.registretion.RegistrationScreen

@Composable
fun navigation() {
    val navController = rememberNavController()
    // ПОКА ЧТО HOME ПОТОМ ПЕРЕККЛЮЧИТЬ НА registretion
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("registration") { RegistrationScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
        }
    }
}