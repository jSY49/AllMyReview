package com.example.allmyreview.reviewRetrofit

import com.example.allmyreview.ApiInterface
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object ReviewRetrofitClient{
    private val baseUrl = "http://allmyreview.dothome.co.kr/"

    var gson = GsonBuilder().setLenient().create()
    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun getRetrofitService(): ApiInterface = getRetrofit().create(ApiInterface::class.java)

}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
    }
}