package com.example.apitestquiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface QuestionApi {
    /*
    @GET("questions?limit=5&categories=science")
    fun getQuestion():Call<List<QuestionModelItem>>*/

    @GET
    fun getQuestion(
        @Url url:String
    ):Call<List<QuestionModelItem>>

}