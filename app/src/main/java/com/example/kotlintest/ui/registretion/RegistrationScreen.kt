package com.example.kotlintest.ui.registretion

import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kotlintest.ui.components.registration.CheckEmailField
import com.example.kotlintest.ui.components.registration.CheckPasswordField
import com.example.kotlintest.ui.components.registration.PrimaryButton
import com.example.kotlintest.ui.components.registration.StudyAppHeader
import com.example.kotlintest.ui.validate.validatePassword

@Composable
fun RegistrationScreen(navController: NavController) {
    var userEmail by remember { mutableStateOf("") }
    var isEmailFormatValid by remember { mutableStateOf(true) }
    var validationMessage by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }

    val testEmail = "example@androidsprint.ru"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
        StudyAppHeader(
            title = "Регистрация",
            subtitle = "Введите почту и пароль",
        )
        Spacer(Modifier.height(70.dp))
        CheckEmailField(
            email = userEmail,
            isEmailValid = isEmailFormatValid,
            onEmailChange = {
                userEmail = it
                isEmailFormatValid = EMAIL_ADDRESS.matcher(it).matches()
                validationMessage = if (!isEmailFormatValid) "Некорректный email" else ""
            },
            onClearClicked = {
                userEmail = ""
                isEmailFormatValid = true
                validationMessage = ""
            },
            testEmail = testEmail,
        )
        Spacer(Modifier.height(20.dp))
        CheckPasswordField(
            password = userPassword,
            isPasswordValid = isPasswordValid,
            onPasswordChange = {
                userPassword = it
                isPasswordValid = validatePassword(it)
            },
            onClearClicked = {
                userPassword = ""
                isPasswordValid = true
            }
        )
        Spacer(Modifier.height(40.dp))
        PrimaryButton(
            text = "Зарегистрироваться",
            onRegisterClock = {
                validationMessage = when {
                    userEmail.isEmpty() || !isEmailFormatValid -> "Некорректный email"
                    userEmail == testEmail -> "Такая почта уже существует"
                    userPassword.isEmpty() || !isPasswordValid -> "Некорректный пароль"
                    else -> "Регистрация успешно пройдена"
                }
            },
            validationMessage = { validationMessage },
            email = userEmail,
            password = userPassword,
            navController = navController
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = validationMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = if (validationMessage.contains("успешно")) Color.DarkGray else Color.Red,
            modifier = Modifier.alpha(if (validationMessage.isNotEmpty()) 1f else 0f)
        )
    }
}




