package ru.otus.composehomework.ui.task3

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.otus.composehomework.R
import androidx.compose.ui.res.stringResource

/**
 * Задание 3: Подключение ViewModel и комплексное состояние (MVI паттерн)
 * 
 * Развивайте Task2Screen, подключив ViewModel для управления состоянием.
 * 
 * MVI (Model-View-Intent) паттерн:
 * - State - единое состояние UI (Task3State) - определено в Task3Contracts.kt
 * - Intent - действия пользователя (Task3Intent) - определено в Task3Contracts.kt
 * - ViewModel обрабатывает Intent и обновляет State
 * 
 * Все контракты MVI (State, Intent, UiState) находятся в файле Task3Contracts.kt
 * 
 * Требования:
 * 1. Скопируйте код из Task2Screen.kt (или начните с Task1Screen, если нужно)
 * 2. Подключите ViewModel через: val viewModel: Task3ViewModel = viewModel()
 * 3. Используйте collectAsState() для наблюдения за единым состоянием:
 *    - val state by viewModel.state.collectAsState()
 *    - Доступ к полям: state.name, state.email, state.message, state.uiState, state.validationErrors
 * 4. Создайте форму с тремя TextField (имя, email, сообщение)
 * 5. Отправляйте Intent через viewModel.handleIntent():
 *    - Task3Intent.NameChanged(name) - при изменении имени
 *    - Task3Intent.EmailChanged(email) - при изменении email
 *    - Task3Intent.MessageChanged(message) - при изменении сообщения
 *    - Task3Intent.SubmitClicked - при нажатии "Отправить"
 *    - Task3Intent.RetryClicked - при нажатии "Повторить"
 *    - Task3Intent.ClearClicked - при нажатии "Очистить"
 * 6. Отображайте разные состояния UI:
 *    - UiState.Loading -> показать CircularProgressIndicator
 *    - UiState.Success -> показать результат
 *    - UiState.Error -> показать сообщение об ошибке
 *    - UiState.ValidationError -> показать ошибки валидации для каждого поля
 * 
 * Подсказки:
 * - Используйте TextField с параметрами value и onValueChange
 * - Для ошибок валидации используйте isError и supportingText
 * - Используйте Column для вертикальной компоновки формы
 * - Примените модификаторы: padding, spacing, fillMaxWidth
 * - Для индикатора загрузки используйте CircularProgressIndicator
 * - Отправляйте Intent: viewModel.handleIntent(Task3Intent.NameChanged(newName))
 */
@Composable
fun Task3Screen() {
    val viewModel = viewModel<Task3ViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text( text = "Форма обратной связи",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp) )

        val nameError = state.validationErrors?.errors?.get(FieldType.NAME)
        TextField(
            value = state.name,
            onValueChange = { viewModel.handleIntent(Task3Intent.NameChanged(it)) },
            label = { Text("Имя") },
            placeholder = { Text(stringResource(R.string.name_hint)) },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError != null,
            supportingText = nameError?.let {{ Text(it) }},
            enabled = state.uiState !is UiState.Loading )

        val mailError = state.validationErrors?.errors?.get(FieldType.EMAIL)
        TextField(
            value = state.email,
            onValueChange = { viewModel.handleIntent(Task3Intent.EmailChanged(it)) },
            label = { Text("Email") },
            placeholder = { Text(stringResource(R.string.email_hint)) },
            modifier = Modifier.fillMaxWidth(),
            isError = mailError != null,
            supportingText = mailError?.let {{ Text(it) }},
            enabled = state.uiState !is UiState.Loading)

        val messageError = state.validationErrors?.errors?.get(FieldType.MESSAGE)
        TextField( value = state.message,
            onValueChange = { viewModel.handleIntent(Task3Intent.MessageChanged(it)) },
            label = { Text("Сообщение") },
            placeholder = { Text(stringResource(R.string.message_hint)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = messageError != null,
            supportingText = messageError?.let {{ Text(it) }},
            enabled = state.uiState !is UiState.Loading )

        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) )
        {
            Button( onClick = { viewModel.handleIntent(Task3Intent.SubmitClicked) },
                enabled = state.uiState !is UiState.Loading,
                modifier = Modifier.weight(1f) )
            {
                Text(stringResource(R.string.submit))
            }

            OutlinedButton( onClick = { viewModel.handleIntent(Task3Intent.ClearClicked) },
                enabled = state.uiState !is UiState.Loading,
                modifier = Modifier.weight(1f) )
            {
                Text(stringResource(R.string.clear))
            }
        }

        when (state.uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 200.dp), contentAlignment = Alignment.Center)
                {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                val successState = state.uiState as UiState.Success
                Card( modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)) )
                {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp))
                    {
                        Text(
                            text = "Успешно!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold)

                        Text(
                            text = "Форма успешно отправлена!",
                            style = MaterialTheme.typography.bodyMedium)

                        Text(
                            text = "ID: ${successState.result.id}",
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            is UiState.Error -> {
                val errorState = state.uiState as UiState.Error
                Card( modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f) )
                ) {
                    Column( modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp) )
                    {
                        Text( text = "Ошибка",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error )

                        Text( text = errorState.message,
                            style = MaterialTheme.typography.bodyMedium )

                        Button(
                            onClick = { viewModel.handleIntent(Task3Intent.RetryClicked) },
                            modifier = Modifier.fillMaxWidth() )
                        {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }
            }
            is UiState.ValidationError -> {}
            is UiState.Idle -> {}
        }
    }
}
