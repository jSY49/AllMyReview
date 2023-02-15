package com.example.allmyreview.addReview

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @PrimaryKey var code : Int,
    var place : String,
    var review :String,
    var date : String
)

