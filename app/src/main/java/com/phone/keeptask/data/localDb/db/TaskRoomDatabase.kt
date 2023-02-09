package com.phone.keeptask.data.localDb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phone.keeptask.data.localDb.dao.TaskDao
import com.phone.keeptask.domain.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskRoomDatabase : RoomDatabase() {
 abstract fun getTask() : TaskDao
}