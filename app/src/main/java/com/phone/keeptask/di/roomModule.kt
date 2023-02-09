package com.phone.keeptask.di

import android.app.Application
import androidx.room.Room
import com.phone.keeptask.data.localDb.db.TaskRoomDatabase
import org.koin.dsl.module


fun provideDao(db: TaskRoomDatabase) = db.getTask()

fun provideBd(application: Application) = Room.databaseBuilder(
    application,
    TaskRoomDatabase::class.java,
    "task_bd"
).fallbackToDestructiveMigration()
    .build()

val roomModule = module {
    single {
        provideBd(get())
    }
    single {
        provideDao(get())
    }
}

