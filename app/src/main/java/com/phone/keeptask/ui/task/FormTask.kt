package com.phone.keeptask.ui.task

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.phone.keeptask.domain.model.Contact
import com.phone.keeptask.domain.model.Navigation
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.helperFunction.Functions
import com.phone.keeptask.ui.task.composables.DatePicker
import com.phone.keeptask.ui.task.composables.Field
import com.phone.keeptask.ui.task.composables.LoadingComposable
import com.phone.keeptask.ui.task.composables.TimePicker
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import java.util.Calendar.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FormTask(
    navController: NavController, task: Task = Task(),
    update: Boolean = false,
    viewModel: TaskViewModel = getViewModel(),
    context: Context = LocalContext.current
) {
    var calendar = Calendar.getInstance()
    val listOptions = mutableListOf<String>()
    var listContact = listOf<Contact>()
    var selectedItem by remember { mutableStateOf(task.contactName) }
    var expanded by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(task.name) }
    var text by remember { mutableStateOf(task.description) }
    var deadTime by remember { mutableStateOf(task.hour) }
    var deadDay by remember { mutableStateOf((task.dayOfYear)) }
    var stateChange by remember { mutableStateOf(false) }
    var hour by remember { mutableStateOf(task.hour) }
    var day by remember { mutableStateOf(task.dayOfYear) }
    when (val contactResponse = viewModel.getAllContactResponse) {
        is Response.Loading -> {
            LoadingComposable()
        }
        is Response.Success -> {
            contactResponse.data.map {
                listOptions += it.name
            }
            listContact = contactResponse.data
        }
        is Response.Error -> {
            Functions.toast(context, "contact non recuperes")
        }
    }
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
                        val task = Task(
                            name = title,
                            description = text,
                            date = calendar.timeInMillis,
                            contactName = selectedItem,
                            contactPhone = Functions.ExtratNumber(listContact, selectedItem),
                            dayOfYear = "%d-%02d-%02d".format(
                                calendar[Calendar.YEAR],
                                calendar[Calendar.MONTH],
                                calendar[Calendar.DAY_OF_MONTH]
                            ),
                            hour = "%02d:%02d".format(
                                calendar[Calendar.HOUR],
                                calendar[Calendar.MINUTE]
                            )
                        )
                        if (!stateChange && update) {
                            viewModel.deleteTask(task)
                            when (val deleteResponse = viewModel.deleteTaskResponse) {
                                is Response.Loading -> {}
                                is Response.Success -> {
                                    navController.navigate(Navigation.Home.route)
                                }
                                is Response.Error -> Functions.toast(
                                    context,
                                    deleteResponse.message
                                )
                            }
                        }
                        if (stateChange && update) {
                            viewModel.updateTask(task)
                            when (val updateResponse = viewModel.updateTaskResponse) {
                                is Response.Loading -> {}
                                is Response.Success -> {
                                    navController.navigate(Navigation.Home.route)
                                }
                                is Response.Error -> Functions.toast(
                                    context,
                                    updateResponse.message
                                )
                            }
                        }
                        if (!update && stateChange) {
                            viewModel.addTask(task)
                            when (val addResponse = viewModel.addTaskResponse) {
                                is Response.Loading -> {}
                                is Response.Success -> {
                                    navController.navigate(Navigation.Home.route)
                                }
                                is Response.Error -> Functions.toast(context, addResponse.message)
                            }
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
