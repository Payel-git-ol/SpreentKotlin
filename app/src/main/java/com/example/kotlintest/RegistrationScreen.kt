package com.example.kotlintest

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlintest.api.sendUserGet
import com.example.kotlintest.validate.validatePassword
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

@Composable
fun RegistrationScreen() {

    //States
    var userEmail by remember { mutableStateOf("") }
    var isEmailFormatValid by remember { mutableStateOf(true) }
    var validationMessage by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }

    //Testing data
    val testEmail = "example@androidsprint.ru"

    Spacer(Modifier.height(50.dp))
    StudyAppHeader(
        title = "Регестрация",
        subtitle = "Введите почту и пароль",
    )
    Spacer(Modifier.height(130.dp))
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
    Spacer(Modifier.height(30.dp))
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
    Spacer(Modifier.height(90.dp))
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
        validationMessage = { validationMessage }, // передаём лямбду
        email = userEmail,
        password = userPassword
    )



    Spacer(Modifier.height(30.dp))
    Text(
        text = validationMessage,
        style = MaterialTheme.typography.bodyLarge,
        color = if (validationMessage.contains("успешно")) Color.DarkGray else Color.Red,
        modifier = Modifier.alpha(if (validationMessage.isNotEmpty()) 1f else 0f)
    )

}

@Composable
@Preview(showBackground = true)
fun StudyAppHeader(
    title: String = "",
    subtitle: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = subtitle,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
fun CheckEmailField(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onClearClicked: () -> Unit,
    testEmail: String,
) {

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        shape = RoundedCornerShape(13.dp),
        textStyle = MaterialTheme.typography.headlineMedium,
        placeholder = {
            Text(
                text = testEmail,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
            )
        },
        singleLine = true,
        label = {
            Text(
                text = if (isEmailValid) "Електропочта" else "Некорректный email",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClearClicked()

                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Иконка очистки поля",
                )
            }
        },
        isError = !isEmailValid && email.isNotEmpty(),
    )
}

@Composable
fun CheckPasswordField(
    password: String,
    isPasswordValid: Boolean,
    onPasswordChange: (String) -> Unit,
    onClearClicked: () -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        shape = RoundedCornerShape(13.dp),
        textStyle = MaterialTheme.typography.headlineMedium,
        placeholder = {
            Text(
                text = "Введите пароль",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
            )
        },
        singleLine = true,
        label = {
            Text(
                text = if (isPasswordValid) "Пароль" else "Слишком простой пароль",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClearClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Очистить пароль"
                )
            }
        },
        isError = !isPasswordValid && password.isNotEmpty(),
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onRegisterClock: () -> Unit,
    validationMessage: () -> String, // функция, которая вернёт актуальное значение
    email: String,
    password: String
) {
    val scope = rememberCoroutineScope()

    Button(
        shape = RoundedCornerShape(13.dp),
        onClick = {
            onRegisterClock()
            val message = validationMessage() // получаем актуальное значение
            if (message.contains("успешно")) {
                Log.i("Register", "Успешная регистрация")
                scope.launch {
                    sendUserGet(email = email, password = password)
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


