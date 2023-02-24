package com.example.allmyreview.signUpRetrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object signUpRetrofitClient{
    private val baseUrl = "http://allmyreview.dothome.co.kr/"

    var gson = GsonBuilder().setLenient().create()
    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun getRetrofitService(): signUpApiInterface = getRetrofit().create(signUpApiInterface::class.java)
    fun getRtService_chckId(): checkIdApiInterface = getRetrofit().create(checkIdApiInterface::class.java)
}

