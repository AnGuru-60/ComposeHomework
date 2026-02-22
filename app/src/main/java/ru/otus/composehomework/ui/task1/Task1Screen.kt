package ru.otus.composehomework.ui.task1

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
 * Задание 1: Переписывание XML на Compose
 * 
 * Перепишите экран из Task1Fragment (XML layout) на Compose.
 * 
 * Требования:
 * 1. Создайте функцию @Composable Task1Screen()
 * 2. Используйте компоненты: Text, Button, Image
 * 3. Используйте Column для вертикальной компоновки
 * 4. Примените модификаторы
 * 5. Реализуйте ту же функциональность: при нажатии кнопки изменяйте текст
 * 
 * Подсказки:
 * - Используйте remember { mutableStateOf() } для хранения состояния текста
 * - Для изображения используйте Image с painterResource или Icon
 * - Примените модификатор .padding(16.dp) к Column
 * - Используйте Arrangement.spacedBy() для отступов между элементами
 */
@Composable
fun Task1Screen() {
    var welcomeText by remember { mutableStateOf("Добро пожаловать!") }

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
            onClick = { welcomeText = "Кнопка нажата!" }
        ) {
            Text("Нажми меня")
        }
    }
}
