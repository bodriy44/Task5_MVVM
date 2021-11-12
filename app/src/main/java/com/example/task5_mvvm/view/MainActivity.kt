package com.example.task5_mvvm.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.task5_mvvm.R
import com.example.task5_mvvm.databinding.ActivityMainBinding
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.fragment.*
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory
import com.example.task5_mvvm.workmanager.BackupWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainActivity : FragmentActivity(), MainView {
    private lateinit var vm: MainViewModel
    private lateinit var recyclerViewFragment: RecyclerViewFragment
    private lateinit var webViewFragment: WebFragment
    private lateinit var animatedHtmlFragment: AnimatedHtmlFragment
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
            buttonWorkmanager.setOnClickListener { v: View? ->
                vm.initWorker()
            }
            buttonWebview.setOnClickListener { v: View? ->
                showWebView()
            }
            buttonCustomTextview.setOnClickListener { v: View? ->
                showCustomTextView()
            }
        }

        setContentView(binding.root)
        setNoteSizeObserver()
        setCurrentNoteObserver()
        setCreateNoteObserver()
        initFragments()
        setSearchTextListener()
        showRecycler()
    }

    override fun setSearchTextListener() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recyclerViewFragment.initAdapter(vm.searchNotes(query))
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recyclerViewFragment.initAdapter(vm.searchNotes(newText))
                return false
            }
        })
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
        webViewFragment = WebFragment()
        animatedHtmlFragment = AnimatedHtmlFragment()
    }

    override fun showRecycler() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, recyclerViewFragment)
            .commit()
    }

    fun showWebView(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, webViewFragment)
            .addToBackStack(null)
            .commit()
    }

    fun showCustomTextView(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, animatedHtmlFragment)
            .addToBackStack(null)
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