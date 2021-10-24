package com.example.task5_mvvm.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.task5_mvvm.R
import com.example.task5_mvvm.adapter.NoteAdapter
import com.example.task5_mvvm.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.MainActivity
import com.example.task5_mvvm.view.OnNoteClickListener
import com.example.task5_mvvm.viewmodel.MainViewModel

class RecyclerViewFragment(var db: AppDatabase, var vm: MainViewModel) :
    Fragment(R.layout.fragment_recycler),
    com.example.task5_mvvm.view.RecyclerView,
    OnNoteClickListener {
    lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_recycler, container, false)
        adapter = NoteAdapter(this)
        inflate.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView).adapter =
            adapter
        inflate.findViewById<FloatingActionButton>(R.id.floatingActionButtonAddNote)
            .setOnClickListener { v: View? -> createNote() }
        return inflate
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
