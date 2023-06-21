package com.example.allmyreview.reviewDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DeleteReviewViewModel @Inject constructor() : ViewModel() {
    val TAG = "deleteReviewViewModel"
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    var state= MutableLiveData<Boolean>()
    private val ReviewService = ReviewRetrofitClient.getRetrofitService()

    fun refresh(reviewId: String) {
        deleteReview(reviewId)
    }

    private fun deleteReview(reviewId: String) {
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val call = ReviewService.deleteReview(reviewId)
            withContext(Dispatchers.Main){
                if (call.isSuccessful) {
                    state.postValue(call.body()!!.success)
                    Log.e(TAG, "delete reveiw call is Successed : ${call.body()}")
                } else {
                    state.postValue(false)
                    Log.e(TAG, "delete reveiw Failed : ${call.raw()}")
                }
            }
        }
    }
}