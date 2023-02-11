package com.phone.keeptask.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phone.keeptask.domain.model.Navigation
import com.phone.keeptask.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FormTask(navController: NavController, task: Task = Task(), update: Boolean = false) {
    var calendar = Calendar.getInstance()
    var listOptions = listOf("Ouandji", "Njosseu", "Nganmeni", "Komegni")
    var selectedItem by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(task.name) }
    var text by remember { mutableStateOf(task.description) }
    var deadTime by remember { mutableStateOf("") }
    var deadDay by remember { mutableStateOf(("")) }
    var stateChange by remember { mutableStateOf(false) }
    var hour by remember { mutableStateOf("00:00") }
    var day by remember { mutableStateOf("2023-01-01") }
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.navigate(Navigation.Home.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            },
            title = {
                Text(text = "Note")
            },
            actions = {
                IconButton(
                    onClick = {
                        if (!stateChange && update) {
                        }
                    }) {
                    Icon(
                        imageVector = if (!stateChange && update) Icons.Default.Delete
                        else Icons.Default.Check,
                        contentDescription = "icons"
                    )
                }

            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {
            Text(
                "$hour ,$day",
                style = MaterialTheme.typography.subtitle2.copy(fontSize = 16.sp),
                color = Color.Black.copy(.25f),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp)
            ) {
                Field(
                    value = title,
                    placeholder = "Titre",
                    onValueChange = {
                        title = it
                        stateChange = true
                    }
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }) {
                    TextField(
                        value = selectedItem,
                        onValueChange = {
                            selectedItem = it
                            stateChange = true
                        },
                        label = {
                            Text(
                                "Personne avec laquelle tu travailleras",
                                style = MaterialTheme.typography.body2.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = Color.Black.copy(.25f)
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        placeholder = {
                            Text(
                                "Nom",
                                style = MaterialTheme.typography.body1.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.Black.copy(.5f)
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            backgroundColor = Color.Transparent
                        )
                    )
                    val filteringOptions =
                        listOptions.filter { it.contains(selectedItem, ignoreCase = true) }
                    if (filteringOptions.isNotEmpty()) ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        filteringOptions.forEach { selectionOption ->
                            DropdownMenuItem(onClick = {
                                selectedItem = selectionOption
                                expanded = false
                            }) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
                DatePicker(
                    label = "Entrer la date de rappel",
                    value = deadDay,
                    onValueChange = { year, month, dayMonth ->
                        deadDay = LocalDate.of(year, month + 1, dayMonth).toString()
                        day = deadDay
                        calendar[MONTH] = month
                        calendar[YEAR] = year
                        calendar[DAY_OF_MONTH] = dayMonth
                    }
                )
                TimePicker(
                    label = "Entrer l'heure de rappel",
                    value = deadTime,
                    onValueChange = { hours, minute ->
                        deadTime = LocalTime.of(hours, minute).toString()
                        hour = deadTime
                        calendar[HOUR_OF_DAY] = hours
                        calendar[MINUTE] = minute
                    }
                )
                Field(
                    value = text,
                    placeholder = "Prendre des notes",
                    onValueChange = {
                        text = it
                        stateChange = true
                    }
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    label: String,
    value: String,
    onValueChange: (Int, Int,Int) -> Unit ,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "yyyy-MM-dd",
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange(year, month, dayOfMonth)
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )

    TextField(
        placeholder = {
            Text(
                text = label, style = MaterialTheme.typography.body2.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color.Black.copy(.25f)
            )
        },
        onValueChange = {},
        value = value,
        enabled = false,
        modifier = Modifier.clickable { dialog.show() }.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun TimePicker(
    label: String,
    value: String,
    onValueChange: (Int, Int) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "HH:mm",
    is24HourView: Boolean = true,
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val time = if (value.isNotBlank()) LocalTime.parse(value, formatter) else LocalTime.now()
    val dialog = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute -> onValueChange(hour, minute) },
        time.hour,
        time.minute,
        is24HourView,
    )

    TextField(
        placeholder = {
            Text(
                text = label, style = MaterialTheme.typography.body2.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color.Black.copy(.25f)
            )
        },
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = Modifier.clickable { dialog.show() }.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Field(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color.Black.copy(.25f)
            )
        },
        maxLines = 30,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}