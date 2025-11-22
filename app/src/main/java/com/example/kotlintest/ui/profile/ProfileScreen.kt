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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kotlintest.ui.components.header.StudyAppHeader

@Composable
fun ProfileScreen(navController: NavController) {
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