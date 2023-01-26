package com.example.apitestquiz

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    fun getRetroClient():Retrofit{
        val gson = GsonBuilder().setLenient().create()
        var okhttpClientBuilder = OkHttpClient.Builder()
        var logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okhttpClientBuilder.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl("https://the-trivia-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttpClientBuilder.build()).build()
    }

}


