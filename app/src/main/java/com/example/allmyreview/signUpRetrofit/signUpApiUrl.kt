package com.example.allmyreview.signUpRetrofit


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class signInApiUrl{
    companion object{
        const val EndPoint ="Register.php"
        const val EndPoint_checkEmail ="UserValidate.php"
    }
}

interface signUpApiInterface{
    @FormUrlEncoded
    @POST(signInApiUrl.EndPoint)
     suspend fun trySignin(
        @Field("UserEmail") UserEmail : String?,
        @Field("UserPwd") UserPwd: String?,
        @Field("UserName") UserName: String
    ): Response<SIGNUP>
}

interface checkEmailApiInterface{
    @FormUrlEncoded
    @POST(signInApiUrl.EndPoint_checkEmail)
    suspend fun checkEmail(
        @Field("UserEmail") UserEmail : String?
    ): Response<CHECK>
}