package com.example.kotlintest.ui.components.registration

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kotlintest.api.sendUserGet
import kotlinx.coroutines.launch

@Composable
fun PrimaryButton(
    text: String,
    onRegisterClock: () -> Unit,
    validationMessage: () -> String,
    email: String,
    password: String,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    Button(
        shape = RoundedCornerShape(13.dp),
        onClick = {
            onRegisterClock()
            val message = validationMessage()
            if (message.contains("успешно")) {
                Log.i("Register", "Успешная регистрация")
                scope.launch {
                    sendUserGet(email = email, password = password)
                }
                navController.navigate("home") {
                    popUpTo("registration") {
                        inclusive = true
                    }
                }
            } else {
                Log.i("Register", "Ошибка регистрации: $message")
            }
        },
        modifier = Modifier
            .height(56.dp)
            .padding(40.dp, 0.dp)
            .fillMaxWidth()
    ) {
        Text(text, style = MaterialTheme.typography.labelMedium)
    }
}