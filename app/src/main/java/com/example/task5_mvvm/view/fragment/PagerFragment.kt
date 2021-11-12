package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task5_mvvm.databinding.FragmentPagerBinding
import com.example.task5_mvvm.model.Note

/**
 *
 * Фрагмент отображения заметки ViewPager
 *
 * @property note отображаемая заметка
 */

class PagerFragment(var note: Note) : Fragment() {
    private var binding: FragmentPagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(note) {
            binding?.let {
                it.noteTitle.text = header
                it.noteDate.text = date
                it.noteText.text = body
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}