package com.example.task5_mvvm.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task5_mvvm.R
import com.example.task5_mvvm.adapter.NoteAdapter
import com.example.task5_mvvm.databinding.FragmentRecyclerBinding
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.OnNoteClickListener
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler),
    com.example.task5_mvvm.view.RecyclerView,
    OnNoteClickListener {
    private lateinit var adapter: NoteAdapter
    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(MainModel(AppDatabase.getDatabase(requireActivity())))
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(layoutInflater).apply {
            floatingActionButtonAddNote.setOnClickListener { v: View? -> createNote() }
            floatingActionButtonDownloadNote.setOnClickListener { v: View? -> handleDownloadButtonClick() }
        }
        initRecycler()
        return binding.root
    }

    override fun initRecycler() {
        adapter = NoteAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        vm.notes.observe(this) {
            initAdapter(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun createNote() {
        vm.createNote()
    }

    override fun onNoteClick(index: Int) {
        vm.setCurrentNoteIndex(index)
    }

    override fun initAdapter(notes: List<Note>) {
        adapter.notes = notes
        adapter.notifyDataSetChanged()
    }

    override fun handleDownloadButtonClick() {
        vm.downloadNote()
        adapter.notifyDataSetChanged()
    }
}
