package com.phone.keeptask.ui.task

import androidx.lifecycle.ViewModel
import com.phone.keeptask.domain.taskRepository.TaskRepository

class TaskViewModel(
    val taskRepository: TaskRepository
) : ViewModel() {

}