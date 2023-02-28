package com.phone.keeptask.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    val id : String="" ,
    @ColumnInfo(name = "name")
    val name : String = "",
    @ColumnInfo(name = "description")
    val description :String = "",
    @ColumnInfo(name = "date")
    val date : Long = 0,
    @ColumnInfo( name= "hour")
    val hour : String = "00:00",
    @ColumnInfo(name = "dayOfYear")
    val dayOfYear : String = "2023-01-01",
    @ColumnInfo(name = "contactName")
    val contactName: String = "",
    @ColumnInfo(name = "contactPhone")
    val contactPhone : String = ""
)
