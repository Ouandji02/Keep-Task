package com.phone.keeptask.ui.task

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun UpdateTask(navController: NavController, id : String) {
    FormTask(navController = navController, update = true)
}