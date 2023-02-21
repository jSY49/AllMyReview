package com.example.allmyreview.reviewRetrofit

import com.example.allmyreview.ApiInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReviewRetrofitClient{
    private val baseUrl = "http://allmyreview.dothome.co.kr/"

    var gson = GsonBuilder().setLenient().create()
    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun getRetrofitService(): ApiInterface = getRetrofit().create(ApiInterface::class.java)
}

