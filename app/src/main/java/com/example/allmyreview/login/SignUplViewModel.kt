package com.example.allmyreview.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.ReviewDb
import com.example.allmyreview.signUpRetrofit.signUpRetrofitClient
import kotlinx.coroutines.*

class SignUplViewModel :ViewModel() {

    val TAG = "SignInlViewModel"
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    private val SignUpService = signUpRetrofitClient.getRetrofitService()
    private val EmailCheckService = signUpRetrofitClient.getRtService_chckEmail()
    var state= MutableLiveData<Boolean>()
    var emailCheck= MutableLiveData<Boolean>()

    fun refresh(name: String, pw: String, email: String){
        trySignIn(name,pw,email)
    }
    private fun trySignIn(name: String, pw: String, email: String){

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = SignUpService.trySignin(email,pw,name)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    state.postValue(true)
                    Log.e(TAG, "singUP is Successed : ${call.raw()}")
                } else {
                    state.postValue(false)
                    Log.e(TAG, "singUp Failed : ${call.raw()}")
                }
            }
        }
    }

    fun checkEmail(email : String){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = EmailCheckService.checkEmail(email)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    emailCheck.postValue(call.body()!!.success)
                    Log.e(TAG, "emailCheck: ${call.raw()}")
                    Log.e(TAG, "emailCheck: ${call.body()}")
                } else {
                    Log.e(TAG, "emailCheck Failed : ${call.raw()}")
                }
            }
        }
    }

}