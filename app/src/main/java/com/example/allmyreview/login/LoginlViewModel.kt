package com.example.allmyreview.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.ReviewDb
import com.example.allmyreview.loginRetorfit.LOGIN
import com.example.allmyreview.loginRetorfit.LoginRetrofitClient
import com.example.allmyreview.signUpRetrofit.signUpRetrofitClient
import kotlinx.coroutines.*

class LoginlViewModel :ViewModel() {

    val TAG = "LoginlViewModel"
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    private val loginService = LoginRetrofitClient.getService_Login()
    var state= MutableLiveData<LOGIN>()

    fun refresh(id: String,pw: String){
        tryLogin(id,pw)
    }
    private fun tryLogin(id: String,pw: String){

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = loginService.getLogin(id,pw)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    state.postValue(call.body())
                    Log.d(TAG, "Login is Successed : ${call.raw()}")
                    Log.d(TAG, "Login is Successed : ${call.body()}")
                } else {
                    Log.e(TAG, "Login Failed : ${call.raw()}")
                }
            }
        }
    }


}