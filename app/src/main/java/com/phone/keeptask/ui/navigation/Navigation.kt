package com.phone.keeptask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.phone.keeptask.domain.model.Navigation
import com.phone.keeptask.ui.task.CreateTask
import com.phone.keeptask.ui.task.Home
import com.phone.keeptask.ui.task.UpdateTask

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.Home.route) {
        composable(Navigation.Home.route) {
            Home(navController)
        }
        composable(Navigation.Update.route + "/{id}", arguments = listOf(
            navArgument("id"){
                nullable = false
                type = NavType.StringType
            }
        )) {entry ->
            entry.arguments?.getString("id")?.let {
                UpdateTask(navController = navController, id = it)
            }
        }
        composable(Navigation.Create.route) {
            CreateTask(navController)
        }
    }
}