package com.phone.keeptask.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id : String ="1",
    @ColumnInfo(name = "name")
    val name : String = "",
    @ColumnInfo(name = "description")
    val description :String = "",
    @ColumnInfo(name = "date")
    val date : Long = 0,
    @ColumnInfo( name= "hour")
    val hour : String = "",
    @ColumnInfo(name = "dayOfYear")
    val dayOfYear : String = "",
    @ColumnInfo(name = "contactName")
    val contactName: String = "",
    @ColumnInfo(name = "contactPhone")
    val contactPhone : String = ""
)
