package com.example.allmyreview

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.allmyreview.users.UserActivity

class UserData(var activity: Activity) {

    private var auto : SharedPreferences = activity.getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
    var UserId=""
    var UserEmail =""
    var UserName=""
    var UserPW=""

    fun getSharedPreference() : Boolean{
        return !auto.getString("userID", null).isNullOrBlank()
    }

    fun addLogin(UserId :String,UserEmail:String, UserName:String,UserPW:String){
        val autoLoginEdit = auto.edit()
        autoLoginEdit.putString("userID", UserId)
        autoLoginEdit.putString("userEmail", UserEmail)
        autoLoginEdit.putString("password",UserPW)
        autoLoginEdit.putString("userName", UserName)
        autoLoginEdit.apply()
    }

    fun getInfo(){
        UserId=auto.getString("userID", null).toString()
        UserEmail =auto.getString("userEmail", null).toString()
        UserName=auto.getString("userName", null).toString()
        UserPW=auto.getString("password", null).toString()
    }

}