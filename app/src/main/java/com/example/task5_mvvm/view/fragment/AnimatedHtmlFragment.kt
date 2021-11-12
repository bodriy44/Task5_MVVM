package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.task5_mvvm.R
import com.example.task5_mvvm.databinding.FragmentMyCustomTextviewBinding

class AnimatedHtmlFragment: Fragment(R.layout.fragment_my_custom_textview) {
    private var binding: FragmentMyCustomTextviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCustomTextviewBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}