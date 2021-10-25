package com.example.task5_mvvm.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.task5_mvvm.R
import com.example.task5_mvvm.adapter.NoteAdapter
import com.example.task5_mvvm.databinding.FragmentRecyclerBinding
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.view.MainActivity
import com.example.task5_mvvm.view.OnNoteClickListener
import com.example.task5_mvvm.viewmodel.MainViewModel

class RecyclerViewFragment(var vm: MainViewModel) :
    Fragment(R.layout.fragment_recycler),
    com.example.task5_mvvm.view.RecyclerView,
    OnNoteClickListener {
    lateinit var adapter: NoteAdapter
    private lateinit var binding: FragmentRecyclerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerBinding.inflate(layoutInflater)
        adapter = NoteAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.floatingActionButtonAddNote.setOnClickListener { v: View? -> createNote() }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.notes.observe(this) {
            initAdapter(it)
        }
    }

    override fun createNote() {
        (activity as MainActivity).showCreateFragment()
    }

    override fun onNoteClick(index: Int) {
        (activity as MainActivity).showNote(vm.getNote(index))
    }

    fun initAdapter(notes: List<Note>) {
        with(adapter) {
            setNotes(notes)
            notifyDataSetChanged()
        }
    }
}
