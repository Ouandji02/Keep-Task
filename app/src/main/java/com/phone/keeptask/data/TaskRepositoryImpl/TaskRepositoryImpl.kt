package com.phone.keeptask.data.TaskRepositoryImpl

import android.content.Context
import com.phone.keeptask.data.contacts.ContentResolverHelper
import com.phone.keeptask.data.localDb.dao.TaskDao
import com.phone.keeptask.domain.model.Contact
import com.phone.keeptask.domain.model.Response
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.domain.taskRepository.TaskRepository
import com.phone.keeptask.helperFunction.Functions
import com.phone.keeptask.receiver.TaskAlarmSchudeler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val contentResolverHelper: ContentResolverHelper,
    private val taskAlarmSchudeler: TaskAlarmSchudeler,
    private val context: Context
) : TaskRepository {
    override fun insertTask(task: Task) = flow {
        try {
            val request = taskDao.insertTask(task)
            taskAlarmSchudeler.taskSchudeler(task)
            Functions.toast(context, "Tache prise en compte")
            emit(Response.Success(request))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun updateTask(task: Task) = flow {
        try {
            val request = taskDao.updateTask(task)
            taskAlarmSchudeler.taskSchudeler(task)
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
            val request = taskDao.deleteTask(task)
            taskAlarmSchudeler.taskCancel(task)
            emit(Response.Success(request))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override fun getContact() = flow {
        try {
            emit(Response.Loading)
            val request = contentResolverHelper.getAllContact()
            emit(Response.Success(request))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }
}