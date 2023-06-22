package com.example.allmyreview.MovieRetrofit

import com.example.allmyreview.ApiUrlInterface
import com.example.allmyreview.Detail_ApiUrlInterface
import com.example.allmyreview.new_ApiUrlInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient{

    private val baseUrl = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun getRetrofitService(): ApiUrlInterface = getRetrofit().create(ApiUrlInterface::class.java)
    @Provides
    @Singleton
    fun getRetrofitService2(): new_ApiUrlInterface = getRetrofit().create(new_ApiUrlInterface::class.java)
    @Provides
    @Singleton
    fun getRetrofitService_Detail(): Detail_ApiUrlInterface = getRetrofit().create(Detail_ApiUrlInterface::class.java)
}

