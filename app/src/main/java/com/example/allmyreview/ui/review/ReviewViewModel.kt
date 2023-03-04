package com.example.allmyreview.ui.review

import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.Review
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import kotlinx.coroutines.*


class ReviewViewModel : ViewModel() {

    val TAG = "ReviewViewModel"
    var loginCheck = MutableLiveData<Boolean>()
    lateinit var fragment :ReviewFragment
    private val reviewService = ReviewRetrofitClient.getRetrofitService()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    val movieLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()
    private fun onError(message: String) {
        movieLoadError.postValue(message)
        loading.postValue(false)
    }

    var data = MutableLiveData<List<Review>>()
    fun refresh(reviewFragment: ReviewFragment) {
        fragment=reviewFragment
        CheckLogin()
    }
    fun CheckLogin() {
        loginCheck.postValue(!getUserId().isNullOrBlank())  //로그인o=true
    }
    private fun getUserId(): String? {
        val auto =fragment.requireActivity().getSharedPreferences("autoLogin", MODE_PRIVATE)
        val userId = auto.getString("userID", null)
        return userId
    }
    fun setId(){
        getReview(getUserId())
    }

    private fun getReview(userId: String?) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = reviewService.getReviewAll(userId)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    data.postValue(call.body()!!.Review)
                    Log.e(TAG, "getReview call is Successed : ${call.body()}")
                } else {
                    Log.e(TAG, "getReview call Failed : ${call.raw()}")
                }
            }
        }
    }
}