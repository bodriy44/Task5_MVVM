package com.example.task5_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase

class MainViewModel(database: AppDatabase): ViewModel() {

    private var _noteCount = MutableLiveData<Int>()
    private var _notes = MutableLiveData<ArrayList<Note>>()
    private var model = MainModel(database)
    var noteCount: LiveData<Int> = _noteCount
    var notes: LiveData<ArrayList<Note>> = _notes

    suspend fun initVM() {
        _notes.value = ArrayList(model.getAllNotes())
        _noteCount.value = _notes.value?.size
        model.notes = notes.value as MutableList<Note>
    }

    fun getIndexNote(note: Note) = model.getIndexNote(note)

    fun getNotes() = model.notes

    fun getSize() = model.getSize()

    suspend fun addNote(title: String, text: String) {
        if (title != "null" && text != "null") {
            model.addNote(title, text)
            _notes.value?.add(Note(title, text))
            Log.i("notes", title)
            _noteCount.value = _noteCount.value?.inc()
        }
    }

    suspend fun deleteNote(note: Note){
        model.deleteNote(note)
    }

    fun getNote(index: Int): Note{
        return model.getNote(index)
    }
}