package com.example.apitestquiz

import retrofit2.Call
import retrofit2.http.GET

interface QuestionApi {
    @GET("questions?limit=5&categories=science")
    fun getQuestion():Call<List<QuestionModelItem>>
}