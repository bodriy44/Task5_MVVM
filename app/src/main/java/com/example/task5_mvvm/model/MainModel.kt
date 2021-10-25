package com.example.task5_mvvm.model

import com.example.task5_mvvm.model.database.AppDatabase
import java.util.*

class MainModel(var db: AppDatabase): IMainModel {
    var notes: MutableList<Note>

    fun getIndexNote(note: Note) = notes.indexOf(note)

    override suspend fun deleteNote(note: Note) {
        db.noteDao().deleteNote(note)
        notes.removeAt(notes.indexOf(note))
    }

    fun getSize() = notes.size

    fun getNote(index: Int) = notes[index]

    override suspend fun addNote(note: Note) {
        db.noteDao().addNote(note)
    }

    override suspend fun addNote(title: String, text: String) {
        addNote(Note(title, text))
    }

    override suspend fun getAllNotes() = db.noteDao().getAll()

    init {
        notes = ArrayList()
    }
}