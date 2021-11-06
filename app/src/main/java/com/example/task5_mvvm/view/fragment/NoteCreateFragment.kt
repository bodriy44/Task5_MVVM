package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task5_mvvm.R
import com.example.task5_mvvm.databinding.FragmentNoteCreateBinding
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory

/**
 *
 * Класс фрагмента, необходимый для создания новой заметки
 *
 * @property binding ViewBinding для элемента NoteCreateFragment
 */
class NoteCreateFragment : Fragment(R.layout.fragment_note_create), com.example.task5_mvvm.view.NoteCreateView {
    private var _binding: FragmentNoteCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity(), MainViewModelFactory(MainModel(AppDatabase.getDatabase(requireActivity())))
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.saveButton.setOnClickListener { v: View? -> addNote() }
        binding.editTextTitle.setText("")
        binding.editTextText.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun addNote() {
        vm.addNote(note)
    }

    val note: Note
        get() = Note(
            binding.editTextTitle.text.toString(),
            binding.editTextText.text.toString(),
            DateFormat.getDateFormat(context).toString(),
        )
}