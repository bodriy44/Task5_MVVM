package com.example.task5_mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "note_title") val header: String,
    @ColumnInfo(name = "note_text") val body: String,
    @ColumnInfo(name = "note_date") val date: String = Date().toString(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)