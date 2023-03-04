package com.example.allmyreview

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewDb(
    @SerializedName("blank")val blank: Boolean,
    @SerializedName("Review") @Expose val Review: List<Review>
)
data class Review(
    @SerializedName("movieCode") @Expose val Moviecode: Int,
    @SerializedName("movieNm") @Expose val movieNm: String,
    @SerializedName("place") @Expose val place: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("date") @Expose val date: String
)

data class AddReview(
    val success: Boolean,
    val message: String
)
