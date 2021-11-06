package com.example.task5_mvvm.view

import com.example.task5_mvvm.model.Note

interface NoteView {
    fun shareNote()
    fun initViewPager()
    fun deleteNote(note: Note)
}