package com.example.task5_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task5_mvvm.model.IMainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(private var repository: IMainModel): ViewModel() {

    private var _noteCount = MutableLiveData<Int>()
    private var _notes = MutableLiveData<ArrayList<Note>>()
    private var _currentNote = MutableLiveData<Note>()
    private var model = repository
    var noteCount: LiveData<Int> = _noteCount
    var notes: LiveData<ArrayList<Note>> = _notes
    var currentNote: LiveData<Note> = _currentNote

    val onSuccessSaveNote = SingleLiveEvent<Unit>()
    val onErrorSaveNote = SingleLiveEvent<Unit>()
    val onSuccessChangeNote = SingleLiveEvent<Unit>()
    val onErrorChangeNote = SingleLiveEvent<Unit>()

    suspend fun initVM() {
        _notes.value = ArrayList(model.getAllNotes())
        _noteCount.value = _notes.value?.size
        model.notes = notes.value as MutableList<Note>
    }

    fun getIndexNote(note: Note) = model.getIndexNote(note)

    fun getNotes() = model.notes

    fun getSize() = model.getSize()

    fun addNote(note: Note) {
        if (note.header != "null" && note.body != "null" && note.header != "" && note.body != "") {
            viewModelScope.launch {
                model.addNote(note.header, note.body)
            }

            _notes.value?.add(Note(note.header, note.body))
            _noteCount.value = _noteCount.value?.inc()
            onSuccessSaveNote.call()
        } else{
            onErrorSaveNote.call()
        }
    }

    fun changeNote(note: Note) {
        if (model.getIndexNote(note)!=-1) {
            _currentNote.value = note
            onSuccessChangeNote.call()
        }else{
            onErrorChangeNote.call()
        }
    }

    fun getCurrentNote() = _currentNote.value!!

    suspend fun deleteNote(note: Note){
        model.deleteNote(note)
    }

    fun getNote(index: Int): Note{
        return model.getNote(index)
    }
}