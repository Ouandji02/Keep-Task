package com.phone.keeptask.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phone.keeptask.domain.model.Contact
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.domain.taskRepository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    val taskRepository: TaskRepository
) : ViewModel() {
    var addTaskResponse by mutableStateOf<Response<Void?>>(Response.Success(null))
        private set
    var updateTaskResponse by mutableStateOf<Response<Void?>>(Response.Success(null))
        private set
    var deleteTaskResponse by mutableStateOf<Response<Void?>>(Response.Success(null))
        private set
    var getAllTaskResponse by mutableStateOf<Response<List<Task>>>(Response.Loading)
        private set
    var getTaskResponse by mutableStateOf<Response<Task?>>(Response.Loading)
        private set
    var getAllContactResponse by mutableStateOf<Response<List<Contact>>>(Response.Loading)
        private set

    init {
        getAllTask()
        getAllContact()
    }

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task).collect {
            addTaskResponse = it
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task).collect {
            updateTaskResponse = it
        }
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task).collect {
            deleteTaskResponse = it
        }
    }

    fun getOneTask(id: String) = viewModelScope.launch {
        taskRepository.getOneTask(id).collect {
            getTaskResponse = it
        }
    }

    private fun getAllTask() = viewModelScope.launch {
        taskRepository.getAllTasks().collect {
            getAllTaskResponse = it
        }
    }

    private fun getAllContact() = viewModelScope.launch {
        taskRepository.getContact().collect {
            getAllContactResponse = it
        }
    }

}