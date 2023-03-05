package com.example.allmyreview.users

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.UserData

class UserViewModel : ViewModel() {

    lateinit var userData: UserData
    var UserId = MutableLiveData<String>()
    var UserEmail = MutableLiveData<String>()
    var UserName = MutableLiveData<String>()
    var UserPW = MutableLiveData<String>()

    fun getIdCheck(userActivity: UserActivity): Boolean {
        userData = UserData(userActivity)
        return userData.getSharedPreference()
    }

    fun getInfo() {
        this.userData.getInfo()
        this.UserId.postValue(userData.UserId)
        this.UserEmail.postValue(userData.UserEmail)
        this.UserName.postValue(userData.UserName)
        this.UserPW.postValue(userData.UserPW)
    }

    fun addInfo(activity: Activity, UserId :String, UserEmail:String, UserName:String, UserPW:String){
        userData = UserData(activity)
        userData.addLogin(UserId  ,UserEmail , UserName,UserPW)
    }

}