package com.example.task5_mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.view.fragment.PagerFragment

/**
 *
 * Данный класс является адаптером для ViewPager
 *
 * @property position2 позиция выбранной в RecyclerView заметки, необходимая для смещения
 * @property size размер списка заметок
 * @property notes список заметок
 */

class PagerAdapter(fragment: Fragment, var position2: Int, var size: Int, var notes: MutableList<Note>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int) = PagerFragment(notes[(position + position2) % size])
}