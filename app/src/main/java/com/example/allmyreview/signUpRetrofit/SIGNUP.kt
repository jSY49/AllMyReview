package com.example.allmyreview.signUpRetrofit

data class SIGNUP(
    val message: String,
    val success: Boolean
)


data class CHECK(
    val success: Boolean,
    val UserEmail : String
)