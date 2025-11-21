package com.example.kotlintest.ui.components.registration

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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