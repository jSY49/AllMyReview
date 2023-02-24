package com.example.allmyreview.loginRetorfit


import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class loginApiUrl{
    companion object{
        const val EndPoint ="Login.php"
    }
}
interface LoginApiInterface{
    @FormUrlEncoded
    @POST(loginApiUrl.EndPoint)
    suspend fun getLogin(
        @Field("UserID") UserID : String?,
        @Field("UserPwd") UserPwd : String?
    ): Response<LOGIN>
}