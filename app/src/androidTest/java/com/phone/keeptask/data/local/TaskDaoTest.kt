package com.phone.keeptask.data.local

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.phone.keeptask.data.localDb.dao.TaskDao
import com.phone.keeptask.data.localDb.db.TaskRoomDatabase
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {
    private lateinit var database: TaskRoomDatabase
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskRoomDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = database.getTask()
    }

    @After
    fun shutdown() {
        database.close()
    }


    @Test
    fun insertInDatabase() = runBlocking {
        val task = Task(
            id = "sfdfqddzsf",
            name = "Anglais",
            description = "Apprendre les verbes",
            date = 1677748838507,
            hour = "10:37",
            dayOfYear = "2023-03-02",
            contactName = "AbouDeBeing",
            contactPhone = "655625509"
        )
        dao.insertTask(task)

        val allTasks = dao.getAllTasks()
        assertThat(allTasks).contains(task)
    }
}