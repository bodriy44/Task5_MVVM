package com.example.task5_mvvm.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task5_mvvm.R
import com.example.task5_mvvm.adapter.NoteAdapter
import com.example.task5_mvvm.databinding.FragmentPagerBinding
import com.example.task5_mvvm.databinding.FragmentRecyclerBinding
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.network.APIService
import com.example.task5_mvvm.network.NoteModel
import com.example.task5_mvvm.view.MainActivity
import com.example.task5_mvvm.view.OnNoteClickListener
import com.example.task5_mvvm.viewmodel.MainViewModel
import com.example.task5_mvvm.viewmodel.MainViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler), com.example.task5_mvvm.view.RecyclerView,
    OnNoteClickListener {
    lateinit var adapter: NoteAdapter
    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: MainViewModel
    private val api = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity(), MainViewModelFactory(MainModel(AppDatabase.getDatabase(requireActivity())))
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(layoutInflater)
        initRecycler()
        binding.floatingActionButtonAddNote.setOnClickListener { v: View? -> createNote() }
        binding.floatingActionButtonDownloadNote.setOnClickListener{ v: View? -> handleDownloadButtonClick()}
        return binding.root
    }

    override fun initRecycler() {
        adapter = NoteAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        vm.notes.observe(this) {
            initAdapter(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    fun handleDownloadButtonClick() {
        api.getNote().enqueue(object : Callback<NoteModel> {
            override fun onResponse(call: Call<NoteModel>, response: Response<NoteModel>) {
                vm.addNote(Note(response.body()!!.title, response.body()!!.body))
            }
            override fun onFailure(call: Call<NoteModel>, t: Throwable?) {
                Log.d("error", "error")
            }
        })
        adapter.notifyDataSetChanged()
    }
}
