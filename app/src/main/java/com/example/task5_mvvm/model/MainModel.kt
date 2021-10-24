package com.example.task5_mvvm.model

import com.example.task5_mvvm.model.database.AppDatabase
import java.util.*

class MainModel(var db: AppDatabase) {
    var notes: MutableList<Note>

    fun getIndexNote(note: Note) = notes.indexOf(note)

    suspend fun deleteNote(note: Note) {
        db.noteDao().deleteNote(note)
        notes.removeAt(notes.indexOf(note))
    }

    fun getSize() = notes.size

    fun getNote(index: Int) = notes[index]

    private suspend fun addNote(note: Note) {
        db.noteDao().addNote(note)
    }

    suspend fun addNote(title: String, text: String) {
        addNote(Note(title, text))
    }

    suspend fun getAllNotes() = db.noteDao().getAll()

    init {
        notes = ArrayList()
    }
}