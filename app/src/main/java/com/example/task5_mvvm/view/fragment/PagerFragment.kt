package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task5_mvvm.databinding.FragmentNoteBinding
import com.example.task5_mvvm.databinding.FragmentPagerBinding
import com.example.task5_mvvm.model.Note

class PagerFragment(var note: Note) : Fragment() {
    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.noteTitle.text = note.header
        binding.noteDate.text = note.date
        binding.noteText.text = note.body
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}