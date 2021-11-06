package com.example.task5_mvvm.view

import com.example.task5_mvvm.model.Note

interface RecyclerView {
    fun createNote()
    fun initRecycler()
    fun initAdapter(notes: List<Note>)
    fun handleDownloadButtonClick()
}