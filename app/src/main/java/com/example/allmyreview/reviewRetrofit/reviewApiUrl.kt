package com.example.allmyreview


import retrofit2.Response
import retrofit2.http.*

class reviewApiUrl {

    companion object {
        const val EndPoint = "getReview.php"
        const val EndPoint_all = "myReviewJson.php"
        const val EndPoint_add = "addReview.php"
        const val EndPoint_update = "updateReview.php"
        const val EndPoint_delete = "deleteReview.php"
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
    @POST(reviewApiUrl.EndPoint_all)
    suspend fun getReviewAll(
        @Field("UserID") UserID : String?,
    ): Response<ReviewDb>

    @FormUrlEncoded
    @POST(reviewApiUrl.EndPoint_add)
    suspend fun addReview(
        @Field("reviewId") reviewId : String?,
        @Field("UserID") UserID : String?,
        @Field("movieCode") movieCode : Int?,
        @Field("movieNm") movieNm : String?,
        @Field("place") place : String?,
        @Field("overview") overview : String?,
        @Field("date") date : String?,
    ): Response<AddReview>

    @FormUrlEncoded
    @POST(reviewApiUrl.EndPoint_update)
    suspend fun updateReview(
        @Field("reviewId") reviewId : String?,
        @Field("place") place : String?,
        @Field("overview") overview : String?,
        @Field("date") date : String?,
    ): Response<AddReview>

    @FormUrlEncoded
    @POST(reviewApiUrl.EndPoint_delete)
    suspend fun deleteReview(
        @Field("reviewId") reviewId : String?,
    ): Response<AddReview>
}


