package com.example.allmyreview.signUpRetrofit


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class signUpiUrl{
    companion object{
        const val EndPoint ="Register.php"
        const val EndPoint_checkEmail ="UserValidate.php"
        const val EndPoint_createTable ="createTable.php"
    }
}

interface signUpApiInterface{
    @FormUrlEncoded
    @POST(signUpiUrl.EndPoint)
     suspend fun trySignup(
        @Field("UserID") UserID : String?,
        @Field("UserEmail") UserEmail : String?,
        @Field("UserPwd") UserPwd: String?,
        @Field("UserName") UserName: String
    ): Response<SIGNUP>
}

interface checkIdApiInterface{
    @FormUrlEncoded
    @POST(signUpiUrl.EndPoint_checkEmail)
    suspend fun checkID(
        @Field("UserID") UserID : String?
    ): Response<CHECK>
}
