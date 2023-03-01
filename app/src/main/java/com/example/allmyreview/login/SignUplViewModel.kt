package com.example.allmyreview.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.ReviewDb
import com.example.allmyreview.signUpRetrofit.signUpRetrofitClient
import kotlinx.coroutines.*

class SignUplViewModel :ViewModel() {

    val TAG = "SignUplViewModel"
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    private val SignUpService = signUpRetrofitClient.getRetrofitService()
    private val IdCheckService = signUpRetrofitClient.getRtService_chckId()
    var state= MutableLiveData<Boolean>()
    var idCheck= MutableLiveData<Boolean>()

    fun refresh(id: String,  name: String, pw: String, email: String){
        trySignIn(id,name,pw,email)
    }
    private fun trySignIn(id: String,name: String, pw: String, email: String){

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = SignUpService.trySignup(id,email,pw,name)
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

    fun checkId(id : String){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = IdCheckService.checkID(id)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    idCheck.postValue(call.body()!!.success)
                    Log.e(TAG, "emailCheck: ${call.raw()}")
                    Log.e(TAG, "emailCheck: ${call.body()}")
                } else {
                    Log.e(TAG, "emailCheck Failed : ${call.raw()}")
                }
            }
        }
    }


}