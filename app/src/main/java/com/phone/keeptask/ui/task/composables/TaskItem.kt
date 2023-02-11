package com.phone.keeptask.ui.task.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.ui.theme.Shapes
import com.phone.keeptask.ui.theme.grayLight

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
                task.name,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = task.description,
                style = MaterialTheme.typography.subtitle2,
                color = Color.Black.copy(.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.hour + ", " + task.dayOfYear,
                style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp),
                modifier = Modifier.padding(top = 10.dp),
                color = Color.Black.copy(.25f)
            )
        }
    }
}