package ru.otus.composehomework.ui.task2

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Задание 2: Добавление состояния
 * 
 * Развивайте Task1Screen, добавив счетчик кликов.
 * 
 * Требования:
 * 1. Скопируйте код из Task1Screen.kt
 * 2. Добавьте счетчик используя: var count by remember { mutableStateOf(0) }
 * 3. Кнопка должна увеличивать счетчик при каждом нажатии
 * 4. Отобразите текущее значение счетчика в Text
 * 
 * Подсказки:
 * - Используйте делегат by для удобной работы с состоянием
 * - Не забудьте импортировать: androidx.compose.runtime.getValue и setValue
 * - Обновите текст кнопки или добавьте отдельный Text для счетчика
 */
@Composable
fun Task2Screen() {
    var welcomeText by remember { mutableStateOf("Добро пожаловать!") }
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = welcomeText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = android.R.drawable.ic_dialog_info),
            contentDescription = "Welcome",
            modifier = Modifier.size(120.dp)
        )

        Button(
            onClick = { count++ }
        ) {
            Text("Нажми меня")
        }

        Text(
            text = "Счетчик: $count",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
