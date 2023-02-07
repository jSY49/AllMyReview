package com.example.allmyreview

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private val baseUrl = " http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getRetrofitService(): ApiUrlService = getRetrofit().create(ApiUrlService::class.java)
}

