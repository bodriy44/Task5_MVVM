package com.example.task5_mvvm.network

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("/posts/1")
    fun getNote(): Call<NoteModel>
}