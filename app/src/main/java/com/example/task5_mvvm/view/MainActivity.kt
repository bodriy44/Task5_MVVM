package com.example.task5_mvvm.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task5_mvvm.R
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.fragment.AboutDialogFragment
import com.example.task5_mvvm.view.fragment.NoteCreateFragment
import com.example.task5_mvvm.view.fragment.NoteFragment
import com.example.task5_mvvm.view.fragment.RecyclerViewFragment
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : FragmentActivity(), MainView {

    private lateinit var vm: MainViewModel
    lateinit var recyclerViewFragment: RecyclerViewFragment
    lateinit var noteFragment: NoteFragment
    private lateinit var noteCreateFragment: NoteCreateFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(this, MainViewModelFactory(AppDatabase.getDatabase(this))).get(
            MainViewModel::class.java
        )

        lifecycleScope.launch {
            vm.initVM()
        }

        noteCreateFragment = NoteCreateFragment()
        noteFragment = NoteFragment(AppDatabase.getDatabase(this), vm)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button_about).setOnClickListener { v: View? ->
            val myDialogFragment = AboutDialogFragment()
            myDialogFragment.show(supportFragmentManager.beginTransaction(), "dialog")
        }

        recyclerViewFragment = RecyclerViewFragment(AppDatabase.getDatabase(this), vm)

        showRecycler()
    }

    override fun showRecycler() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, recyclerViewFragment)
            .commit()
    }

    override fun showNote(note: Note) {
        noteFragment.changeNote(note)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, noteFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showCreateFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, noteCreateFragment)
            .addToBackStack(null)
            .commit()
    }

    fun newNote(note: Note){
        lifecycleScope.launch {
            vm.addNote(note.header, note.body)
        }
        showRecycler()
    }
}