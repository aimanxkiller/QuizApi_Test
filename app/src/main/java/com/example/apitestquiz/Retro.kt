package com.example.apitestquiz

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    fun getRetroClient():Retrofit{
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://the-trivia-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}