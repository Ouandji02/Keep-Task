package com.phone.keeptask.domain.taskRepository

import com.phone.keeptask.domain.model.Contact
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun insertTask(task: Task): Flow<Response<Void>>
    fun updateTask(task: Task): Flow<Response<Void>>
    fun getOneTask(id: String): Flow<Response<Task>>
    fun getAllTasks(): Flow<Response<List<Task>>>
    fun deleteTask(task: Task): Flow<Response<Void>>
    fun getContact() : Flow<Response<List<Contact>>>
}