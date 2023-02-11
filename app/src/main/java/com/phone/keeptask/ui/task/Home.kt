package com.phone.keeptask.ui.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phone.keeptask.domain.model.Navigation
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.ui.theme.Shapes
import com.phone.keeptask.ui.theme.grayLight

@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = {
            Text(
                text = "Note",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
            )
        },
        floatingActionButton = {
            floatingButton(navController)
        }) {
        LazyColumn() {
            items(10) {
                TaskItem(
                    onNavigate = { navController.navigate(Navigation.Update.route+"/mgkj") },
                    Task()
                )
            }
        }
    }
}

@Composable
fun floatingButton(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Navigation.Create.route)
            },
            modifier = Modifier.size(50.dp),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "icons", tint = Color.White)
        }
    }
}

@Composable
fun TaskItem(onNavigate: () -> Unit, task: Task) {
    Surface(
        color = grayLight.copy(.35f),
        shape = Shapes.medium,
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .fillMaxWidth()
            .clickable { onNavigate.invoke() }
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)) {
            Text(
                "First text",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Second text",
                style = MaterialTheme.typography.subtitle2,
                color = Color.Black.copy(.4f)
            )
            Text(
                text = "Date de creation",
                style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp),
                modifier = Modifier.padding(top = 10.dp),
                color = Color.Black.copy(.25f)
            )
        }
    }
}
