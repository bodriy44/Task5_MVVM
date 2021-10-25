package com.example.task5_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task5_mvvm.model.IMainModel
import com.example.task5_mvvm.model.database.AppDatabase

class MainViewModelFactory(private var repository: IMainModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IMainModel::class.java).newInstance(repository)
    }
}