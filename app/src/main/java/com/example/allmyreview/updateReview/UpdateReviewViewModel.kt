package com.example.allmyreview.updateReview

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateReviewViewModel :ViewModel() {

    val TAG = "updateReviewViewModel"
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    var state= MutableLiveData<Boolean>()
    private val ReviewService = ReviewRetrofitClient.getRetrofitService()

    fun refresh(reviewId: String, p: String, review: String) {
        updateReview(reviewId, p, review,getdate())
    }

    private fun updateReview(
        reviewId: String,
        p: String,
        review: String,
        date: String
    ) {
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val call = ReviewService.updateReview(reviewId, p, review,date)
            withContext(Dispatchers.Main){
                if (call.isSuccessful) {
                    state.postValue(call.body()!!.success)
                    Log.e(TAG, "update reveiw call is Successed : ${call.body()}")
                } else {
                    state.postValue(false)
                    Log.e(TAG, "update reveiw Failed : ${call.raw()}")
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getdate(): String {
        val calendar: Calendar = GregorianCalendar()
        val SDF: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        SDF.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val res = SDF.format(calendar.time)
        return res
    }
}