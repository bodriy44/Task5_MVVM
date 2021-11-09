package com.example.task5_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.task5_mvvm.model.IMainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.network.APIService
import com.example.task5_mvvm.network.NoteModel
import com.example.task5_mvvm.utils.SingleLiveEvent
import com.example.task5_mvvm.workmanager.BackupWorker
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Класс основной ViewModel
 *
 * @property repository объект для взаимодействия с данными
 * @property noteIndex индекс текущей заметки во ViewPager
 * @property currentNote текущая заметка во ViewPager
 * @property api интерфейс необходимй для скачивания заметки
 *
 */

class MainViewModel(private var repository: IMainModel) : ViewModel() {

    private var _noteCount = MutableLiveData<Int>()
    private var _noteIndex = MutableLiveData<Int>()
    private var _notes = MutableLiveData<MutableList<Note>>()
    private var _currentNote = MutableLiveData<Note>()
    private val api = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIService::class.java)

    var noteIndex: LiveData<Int> = _noteIndex
    var noteCount: LiveData<Int> = _noteCount
    var notes: LiveData<MutableList<Note>> = _notes
    var currentNote: LiveData<Note> = _currentNote

    val onSuccessSaveNote = SingleLiveEvent<Unit>()
    val onErrorSaveNote = SingleLiveEvent<Unit>()
    val onSuccessChangeNote = SingleLiveEvent<Unit>()
    val onErrorChangeNote = SingleLiveEvent<Unit>()
    val onCreateNote = SingleLiveEvent<Unit>()

    suspend fun initVM() {
        _notes.value = ArrayList(repository.getAllNotes())
        _noteCount.value = _notes.value?.size
        repository.notes = notes.value as MutableList<Note>
    }

    fun createNote() {
        onCreateNote.call()
    }

    fun getIndexNote(note: Note) = repository.getIndexNote(note)

    fun getNotes() = repository.notes

    fun getNotesSize() = repository.getNotesSize()

    fun addNote(note: Note) {
        if (note.header != "null" && note.body != "null" && note.header != "" && note.body != "") {
            viewModelScope.launch {
                repository.addNote(note.header, note.body)
            }

            _notes.value?.add(Note(note.header, note.body))
            _noteCount.value = _noteCount.value?.inc()
            onSuccessSaveNote.call()
        } else {
            onErrorSaveNote.call()
        }
    }

    fun changeCurrentNote() {
        if (noteIndex.value != -1 && noteIndex.value != null) {
            _currentNote.value = _notes.value!!.get(noteIndex.value!!)
            onSuccessChangeNote.call()
        } else {
            onErrorChangeNote.call()
        }
    }

    fun getCurrentNote() = _currentNote.value

    fun setCurrentNote(note: Note){
        _currentNote.value = note
    }

    suspend fun deleteNote(note: Note) {
        repository.deleteNote(note)
        _noteCount.value = _noteCount.value?.dec()
    }

    fun getNote(index: Int) = repository.getNote(index)

    fun downloadNote() {
        api.getNote().enqueue(object : Callback<NoteModel> {
            override fun onResponse(call: Call<NoteModel>, response: Response<NoteModel>) {
                addNote(Note(response.body()!!.title, response.body()!!.body))
            }

            override fun onFailure(call: Call<NoteModel>, t: Throwable?) {
                Log.d("error", "error")
            }
        })
    }

    fun searchNotes(str: String): MutableList<Note>{
        val newNotes = mutableListOf<Note>()
        for(note in getNotes()){
            if(note.header.contains(str))
                newNotes.add(note)
        }
        _notes.value = newNotes
        return newNotes
    }

    fun setCurrentNoteIndex(index: Int) {
        _noteIndex.value = index
    }

    fun initWorker(){
        WorkManager.getInstance().enqueue(
            PeriodicWorkRequest.Builder(
                BackupWorker::class.java, 30,
                TimeUnit.MINUTES
            ).build());
    }
}