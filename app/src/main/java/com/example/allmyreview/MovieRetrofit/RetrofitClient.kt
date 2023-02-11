package com.example.allmyreview.MovieRetrofit

import com.example.allmyreview.ApiUrlInterface
import com.example.allmyreview.new_ApiUrlInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private val baseUrl = "https://api.themoviedb.org/3/"

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getRetrofitService(): ApiUrlInterface = getRetrofit().create(ApiUrlInterface::class.java)
    fun getRetrofitService2(): new_ApiUrlInterface = getRetrofit().create(new_ApiUrlInterface::class.java)
}

