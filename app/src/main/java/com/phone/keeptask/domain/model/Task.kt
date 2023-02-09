package com.phone.keeptask.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id : String,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "description")
    val description :String,
    @ColumnInfo(name = "date")
    val date : Int,
    @ColumnInfo(name = "contactName")
    val contactName: String? = null,
    @ColumnInfo(name = "contactPhone")
    val contactPhone : String? = null
)
