package com.example.task5_mvvm.view.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.task5_mvvm.R
import com.example.task5_mvvm.adapter.PagerAdapter
import com.example.task5_mvvm.constants.SENDER_INFO
import com.example.task5_mvvm.databinding.FragmentNoteBinding
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.NoteView
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

/**
 *
 * Класс фрагмента, необходимого для вывода данных кнкретной заметки на экран
 *
 * @property binding ViewBinding для элемента NoteFragment
 *
 */

class NoteFragment : Fragment(R.layout.fragment_note), NoteView {
    private lateinit var adapter: PagerAdapter
    private var viewPager: ViewPager2? = null
    private lateinit var vm: MainViewModel
    private var binding: FragmentNoteBinding? = null

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
    ): View? {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding?.root
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        with(binding) {
            this?.floatingActionButtonDelete?.setOnClickListener { v: View? ->
                deleteNote(
                    vm.getNotes()[(viewPager?.currentItem
                        ?: 0 + adapter.positionOffset) % adapter.size]
                )
            }
            this?.floatingActionButtonShare?.setOnClickListener { v: View? -> shareNote() }
        }
        initViewPager()
    }

    override fun initViewPager() {
        adapter = PagerAdapter(
            this,
            vm.getIndexNote(vm.getCurrentNote() ?: Note("", "")),
            vm.getNotesSize(),
            vm.getNotes()
        )

        viewPager = binding?.pager?.apply { isSaveEnabled = false }
        viewPager?.let {
            it.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun shareNote() {
        vm.getNotes()[(viewPager?.currentItem ?: 0 + adapter.positionOffset) % adapter.size].let {
            startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "\n${it.date} ${it.header} ${it.body} $SENDER_INFO"
                        )
                        type = "text/plain"
                    },
                    null
                )
            )
        }
    }

    override fun deleteNote(note: Note) {
        lifecycleScope.launch {
            vm.deleteNote(note)
        }
    }
}