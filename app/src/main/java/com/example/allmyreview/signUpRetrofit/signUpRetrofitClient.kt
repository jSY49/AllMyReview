package com.example.allmyreview.signUpRetrofit

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object signUpRetrofitClient{
    private val baseUrl = "http://allmyreview.dothome.co.kr/"

    var gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun getRetrofitService(): signUpApiInterface = getRetrofit().create(signUpApiInterface::class.java)
    @Provides
    @Singleton
    fun getRtService_chckId(): checkIdApiInterface = getRetrofit().create(checkIdApiInterface::class.java)
}

