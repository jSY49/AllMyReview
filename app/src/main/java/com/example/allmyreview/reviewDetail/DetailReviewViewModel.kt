package com.example.allmyreview.reviewDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.MovieRetrofit.RetrofitClient
import com.example.allmyreview.ReviewDb
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import kotlinx.coroutines.*

class DetailReviewViewModel: ViewModel() {
    val TAG = "DetailReviewViewModel"
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    private val ReviewService = ReviewRetrofitClient.getRetrofitService()
    var review = MutableLiveData<ReviewDb>()


    fun refresh(id : Int,userId: String?) {
        getReview(id,userId)
    }

    private fun getReview(code: Int,userId: String?) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ReviewService.getReview(code,userId)
            Log.d(TAG, response.raw().toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    review.postValue(response.body())
                    Log.d(TAG, response.body().toString())
                    Log.d(TAG,"value: ${response.body().toString()}")
                } else {
                    Log.e(TAG, "Error: ${response.message()}")

                }
            }
        }
    }


}