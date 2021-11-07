package com.example.task5_mvvm.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task5_mvvm.R
import com.example.task5_mvvm.databinding.ActivityMainBinding
import com.example.task5_mvvm.model.MainModel
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
    private lateinit var recyclerViewFragment: RecyclerViewFragment
    private lateinit var noteFragment: NoteFragment
    private lateinit var noteCreateFragment: NoteCreateFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            buttonAbout.setOnClickListener { v: View? ->
                AboutDialogFragment().show(supportFragmentManager.beginTransaction(), "dialog")
            }
        }
        setContentView(binding.root)
        setNoteSizeObserver()
        setCurrentNoteObserver()
        setCreateNoteObserver()
        initFragments()
        showRecycler()
    }

    override fun initViewModel() {
        vm = ViewModelProvider(
            this,
            MainViewModelFactory(MainModel.getRepository(AppDatabase.getDatabase(this)))
        ).get(MainViewModel::class.java)

        lifecycleScope.launch {
            vm.initVM()
        }
    }

    override fun initFragments() {
        noteCreateFragment = NoteCreateFragment()
        noteFragment = NoteFragment()
        recyclerViewFragment = RecyclerViewFragment()
    }

    override fun showRecycler() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, recyclerViewFragment)
            .commit()
    }

    override fun setNoteSizeObserver() {
        vm.noteCount.observe(this) {
            showRecycler()
        }
    }

    override fun setCurrentNoteObserver() {
        vm.noteIndex.observe(this) {
            showNote()
        }
    }

    override fun showNote() {
        vm.changeCurrentNote()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, noteFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun setCreateNoteObserver() {
        vm.onCreateNote.observe(this) {
            showCreateFragment()
        }
    }

    override fun showCreateFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, noteCreateFragment)
            .addToBackStack(null)
            .commit()
    }
}