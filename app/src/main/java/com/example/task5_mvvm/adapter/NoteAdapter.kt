package com.example.task5_mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task5_mvvm.R
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.view.OnNoteClickListener
import java.util.*

class NoteAdapter(private val onNoteClickListener: OnNoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var notes: List<Note> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
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

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateView: TextView
        val headerView: TextView

        init {
            dateView = itemView.findViewById<TextView>(R.id.date)
            headerView = itemView.findViewById<TextView>(R.id.title)
        }
    }
}