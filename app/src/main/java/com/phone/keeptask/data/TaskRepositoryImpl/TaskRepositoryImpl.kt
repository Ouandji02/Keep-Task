package com.phone.keeptask.data.TaskRepositoryImpl

import com.phone.keeptask.data.contacts.ContentResolverHelper
import com.phone.keeptask.data.localDb.dao.TaskDao
import com.phone.keeptask.domain.model.Contact
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.domain.taskRepository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val contentResolverHelper: ContentResolverHelper
) : TaskRepository {
    override fun insertTask(task: Task) = flow {
        try {
            emit(Response.Loading)
            val request = taskDao.insertTask(task)
            emit(Response.Success(request))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun updateTask(task: Task) = flow {
        try {
            emit(Response.Loading)
            val request = taskDao.updateTask(task)
            emit(Response.Success(request))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun getOneTask(id: String) = flow {
        try {
            emit(Response.Loading)
            val task = taskDao.getOneTask(id)
            emit(Response.Success(task))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun getAllTasks() = flow {
        try {
            emit(Response.Loading)
            val tasks = taskDao.getAllTasks()
            emit(Response.Success(tasks))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun deleteTask(task: Task) = flow {
        try {
            emit(Response.Loading)
            val request = taskDao.deleteTask(task)
            emit(Response.Success(request))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun getContact(): Flow<Response<Contact>> {
        TODO("Not yet implemented")
    }
}