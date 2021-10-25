package com.example.task5_mvvm.model

interface IMainModel {
    suspend fun deleteNote(note: Note)
    suspend fun addNote(note: Note)
    suspend fun addNote(title: String, text: String)
    suspend fun getAllNotes():  List<Note>
}