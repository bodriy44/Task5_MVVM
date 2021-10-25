package com.example.task5_mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.view.fragment.PagerFragment

class PagerAdapter(fragment: Fragment, var position2: Int, var size: Int, var notes: MutableList<Note>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int) = PagerFragment(notes[(position + position2) % size])
}