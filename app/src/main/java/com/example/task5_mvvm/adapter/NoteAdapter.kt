package com.example.task5_mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task5_mvvm.databinding.RecyclerItemBinding
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.view.OnNoteClickListener
import java.util.*

/**
 *
 * Данный класс является адаптером для RecyclerView
 *
 * @property onNoteClickListener обработчик события нажатия на заметку.
 * @property notes список заметок
 * @property binding ViewBinding для элемента RecyclerView
 */

class NoteAdapter(private val onNoteClickListener: OnNoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var notes: List<Note> = ArrayList()
    private lateinit var binding: RecyclerItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        with(holder){
            headerView.text = note.header
            dateView.text = note.date
            itemView.setOnClickListener { v: View? ->
                onNoteClickListener.onNoteClick(position)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun setNotes(notes: List<Note>) {
        this.notes = notes
    }

    fun getNotes(): List<Note> = this.notes

    class ViewHolder(binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val dateView: TextView = binding.date
        val headerView: TextView = binding.title
    }
}