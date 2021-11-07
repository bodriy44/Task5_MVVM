package com.example.task5_mvvm.model

import com.example.task5_mvvm.model.database.AppDatabase
import java.util.*

/**
 *
 * Класс для взаимодействия с данными являющийся синглтоном
 *
 * @property db база данных приложения
 * @property notes список заметок
 */

open class MainModel(var db: AppDatabase) : IMainModel {
    override var notes: MutableList<Note> = ArrayList()

    override fun getIndexNote(note: Note) = notes.indexOf(note)

    override suspend fun deleteNote(note: Note) {
        db.noteDao().deleteNote(note)
        notes.removeAt(notes.indexOf(note))
    }

    override fun getNotesSize() = notes.size

    override fun getNote(index: Int) = notes[index]

    override suspend fun addNote(note: Note) {
        db.noteDao().addNote(note)
    }

    override suspend fun addNote(title: String, text: String) {
        addNote(Note(title, text))
    }

    override suspend fun getAllNotes() = db.noteDao().getAllNotes()

    companion object {
        private var INSTANCE: MainModel? = null
        fun getRepository(db: AppDatabase): MainModel {
            if (INSTANCE != null) {
                return INSTANCE as MainModel
            } else {
                INSTANCE = MainModel(db)
                return INSTANCE as MainModel
            }
        }
    }
}