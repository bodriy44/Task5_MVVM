package com.example.task5_mvvm.view

interface MainView {
    fun showCreateFragment()
    fun showRecycler()
    fun showNote()
    fun initFragments()
    fun setCurrentNoteObserver()
    fun setCreateNoteObserver()
    fun setNoteSizeObserver()
    fun initViewModel()
    fun setSearchTextListener()
}