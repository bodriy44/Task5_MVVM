package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.task5_mvvm.R
import com.example.task5_mvvm.databinding.FragmentNoteCreateBinding
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.view.MainActivity

class NoteCreateFragment : Fragment(R.layout.fragment_note_create), com.example.task5_mvvm.view.NoteCreateView {
    private lateinit var binding: FragmentNoteCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.saveButton.setOnClickListener { v: View? -> addNote() }
        binding.editTextTitle.setText("")
        binding.editTextText.setText("")
    }

    override fun addNote() {
        (activity as MainActivity).newNote(note)
    }

    val note: Note
        get() = Note(
            binding.editTextTitle.text.toString(),
            binding.editTextText.text.toString(),
            DateFormat.getDateFormat(context).toString(),
        )
}