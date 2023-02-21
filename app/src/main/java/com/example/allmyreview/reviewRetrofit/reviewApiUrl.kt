package com.example.allmyreview


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

class reviewApiUrl{

    companion object{
        //const val EndPoint ="myReviewJson.php"
        const val EndPoint ="getReview.php"
    }

}

interface ApiInterface{
    @GET(reviewApiUrl. EndPoint)
    suspend fun getReview(
        @Query("movieCode") movieCode :Int
    ): Response<ReviewDb>


}
