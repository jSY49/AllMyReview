package com.example.allmyreview.loginRetorfit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRetrofitClient{
    private val baseUrl = "http://allmyreview.dothome.co.kr/"

    var gson = GsonBuilder().setLenient().create()
    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun getService_Login(): LoginApiInterface = getRetrofit().create(LoginApiInterface::class.java)
}

