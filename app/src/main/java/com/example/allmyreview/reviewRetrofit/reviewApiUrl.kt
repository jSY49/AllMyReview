package com.example.allmyreview


import retrofit2.Response
import retrofit2.http.*

class reviewApiUrl {

    companion object {
        const val EndPoint = "getReview.php"
        const val EndPoint_add = "addReview.php"
    }

}

interface ApiInterface {
    @FormUrlEncoded
    @POST(reviewApiUrl.EndPoint)
    suspend fun getReview(
        @Field("movieCode") movieCode: Int,
        @Field("UserID") UserID: String?
    ): Response<ReviewDb>

    @FormUrlEncoded
    @POST(reviewApiUrl.EndPoint_add)
    suspend fun addReview(
        @Field("reviewId") reviewId : String?,
        @Field("UserID") UserID : String?,
        @Field("movieCode") movieCode : Int?,
        @Field("place") place : String?,
        @Field("overview") overview : String?,
        @Field("date") date : String?,
    ): Response<AddReview>
}


