package com.example.task5_mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.fragment.PagerFragment
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory

class PagerAdapter(fragment: Fragment, var position2: Int, var size: Int, var notes: MutableList<Note>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): PagerFragment
    {

        return PagerFragment(notes[(position + position2) % size])
    }
}