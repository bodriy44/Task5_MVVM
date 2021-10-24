package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.task5_mvvm.R
import com.example.task5_mvvm.model.Note

class PagerFragment(var note: Note) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(view) {
            findViewById<TextView>(R.id.noteTitle).text = note.header
            findViewById<TextView>(R.id.noteDate).text = note.date
            findViewById<TextView>(R.id.noteText).text = note.body
        }
    }
}