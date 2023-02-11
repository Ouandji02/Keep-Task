package com.phone.keeptask.ui.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.ui.task.composables.LoadingComposable
import org.koin.androidx.compose.getViewModel

@Composable
fun UpdateTask(navController: NavController, id: String) {
    val taskViewModel: TaskViewModel = getViewModel()
    LaunchedEffect(key1 = id) {
        taskViewModel.getOneTask(id)
    }
    when (val response = taskViewModel.getTaskResponse) {
        is Response.Loading -> LoadingComposable()
        is Response.Success -> {
            response.data?.let {
                FormTask(
                    navController = navController,
                    update = true,
                    task = it
                )
            }
        }
        else -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Il y a pas de tache pour le moment",
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

        }
    }
}