package com.phone.keeptask.data.localDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.phone.keeptask.domain.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task) : Void
    @Update
    suspend fun updateTask(task: Task) : Void
    @Query("SELECT* FROM Task WHERE id = :id")
    suspend fun getOneTask(id: String) : Task
    @Query("SELECT* FROM Task")
    suspend fun getAllTasks() : List<Task>
    @Delete
    suspend fun deleteTask(task: Task) : Void
}