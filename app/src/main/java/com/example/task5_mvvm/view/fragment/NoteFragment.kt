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
 * @property adapter адаптер для ViewPager
 * @property binding ViewBinding для элемента NoteFragment
 *
 */

class NoteFragment : Fragment(R.layout.fragment_note), NoteView {
    private lateinit var adapter: PagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var vm: MainViewModel
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity(), MainViewModelFactory(MainModel(AppDatabase.getDatabase(requireActivity())))
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        binding.floatingActionButtonDelete.setOnClickListener { v: View? ->
            deleteNote(vm.getNotes()[(viewPager.currentItem + adapter.positionOffset) % adapter.size])
        }
        binding.floatingActionButtonShare.setOnClickListener { v: View? -> shareNote() }
        initViewPager()
    }

    override fun initViewPager() {
        adapter = PagerAdapter(this, vm.getIndexNote(vm.getCurrentNote()?:Note("","")), vm.getNotesSize(),  vm.getNotes())
        viewPager = binding.pager
        viewPager.adapter = adapter
        viewPager.isSaveEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun shareNote() {
        val note = vm.getNotes()[(viewPager.currentItem + adapter.positionOffset) % adapter.size]
        val sendIntent = Intent()
        with(sendIntent) {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "\n${note.date} ${note.header} ${note.body} Отправлено из приложения MyNote"
            )
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

    override fun deleteNote(note: Note) {
        lifecycleScope.launch {
            vm.deleteNote(note)
        }
    }
}