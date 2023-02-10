package com.phone.keeptask.di

import com.phone.keeptask.data.TaskRepositoryImpl.TaskRepositoryImpl
import com.phone.keeptask.data.contacts.ContentResolverHelper
import com.phone.keeptask.domain.taskRepository.TaskRepository
import com.phone.keeptask.receiver.TaskAlarmSchudeler
import com.phone.keeptask.receiver.TaskAlarmSchudelerImpl
import com.phone.keeptask.ui.task.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskModule = module {
    single {
        ContentResolverHelper(get())
    }
    single<TaskAlarmSchudeler> {
        TaskAlarmSchudelerImpl(get())
    }
    single<TaskRepository> {
        TaskRepositoryImpl(get(), get(), get())
    }
    viewModel {
        TaskViewModel(get())
    }
}