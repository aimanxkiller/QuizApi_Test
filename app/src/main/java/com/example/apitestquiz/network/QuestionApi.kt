package com.example.apitestquiz.network

import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.model.QuizCat
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("https://the-trivia-api.com/api/questions?limit=5")
    suspend fun getQuestionCat(
        @Query("categories") categories:String
    ): Response<List<QuestionModelItem>>

    @GET("https://the-trivia-api.com/api/categories")
    fun getCategories():Call<QuizCat>

}