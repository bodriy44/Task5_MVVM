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
 * @property binding ViewBinding для элемента RecyclerView
 */

class NoteAdapter(private val onNoteClickListener: OnNoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var notes: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(notes[position])
            itemView.setOnClickListener { v: View? ->
                onNoteClickListener.onNoteClick(position)
            }
        }
    }

    override fun getItemCount() = notes.size

    class ViewHolder(binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            headerView.text = note.header
            dateView.text = note.date
        }

        val dateView: TextView = binding.date
        val headerView: TextView = binding.title
    }
}