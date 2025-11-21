package com.example.kotlintest.ui.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlintest.ui.home.HomeScreen
import com.example.kotlintest.ui.registretion.RegistrationScreen

@Composable
fun navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "registration") {
        composable("registration") { RegistrationScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}