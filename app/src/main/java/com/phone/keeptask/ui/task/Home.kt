package com.phone.keeptask.ui.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phone.keeptask.domain.model.Navigation
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.helperFunction.Functions
import com.phone.keeptask.ui.task.composables.FloatingButton
import com.phone.keeptask.ui.task.composables.LoadingComposable
import com.phone.keeptask.ui.task.composables.TaskItem
import org.koin.androidx.compose.getViewModel

@Composable
fun Home(navController: NavController) {
    val taskViewModel = getViewModel<TaskViewModel>()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Text(
                text = "Note",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
            )
        },
        floatingActionButton = {
            FloatingButton(navController)
        }) {
        when (val response = taskViewModel.getAllTaskResponse) {
            is Response.Loading -> LoadingComposable()
            is Response.Success -> {
                if (response.data.isEmpty()) Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Il y a pas de tache pour le moment",
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                else LazyColumn() {
                    items(items = response.data) { task ->
                        TaskItem(
                            onNavigate = { navController.navigate(Navigation.Update.route + "/${task.id}") },
                            task
                        )
                        println("dssssssssssssssssssssssss ${task.date}")
                    }
                }
            }
            is Response.Error -> {
                Functions.toast(context, response.message)
            }
        }

    }
}




