package com.example.task5_mvvm.view

import com.example.task5_mvvm.model.Note

interface MainView {
    fun showCreateFragment()
    fun showRecycler()
    fun showNote(note: Note)
}