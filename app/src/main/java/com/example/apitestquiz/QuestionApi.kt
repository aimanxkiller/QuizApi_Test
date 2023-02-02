package com.example.apitestquiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface QuestionApi {

    @GET("https://the-trivia-api.com/api/questions?limit=5")
    fun getQuestionCat(
        @Query("categories") categories:String
    ): Call<List<QuestionModelItem>>

//    @GET
//    fun getQuestion(
//        @Url url:String
//    ):Call<List<QuestionModelItem>>

    @GET("https://the-trivia-api.com/api/categories")
    fun getCategories():Call<QuizCat>

}