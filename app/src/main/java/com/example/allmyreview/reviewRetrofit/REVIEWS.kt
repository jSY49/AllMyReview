package com.example.allmyreview

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewDb(
    @SerializedName("Review") @Expose val Review: List<Review>
)
data class Review(
    @SerializedName("Moviecode") @Expose val Moviecode: Int,
    @SerializedName("place") @Expose val place: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("date") @Expose val date: String
)
