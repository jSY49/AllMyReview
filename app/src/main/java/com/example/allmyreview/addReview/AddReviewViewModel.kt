package com.example.allmyreview.addReview

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddReviewViewModel @Inject constructor() :ViewModel() {

    val TAG="AddReviewViewModel"
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    var state= MutableLiveData<Boolean>()
    private val reviewService = ReviewRetrofitClient.getRetrofitService()

    fun refresh(
        reviewId:String,
        UserID:String, movieCode: Int,
        movieNm:String,star: Float, place:String,
         overview:String, date:String){
        add(reviewId,UserID,movieCode,movieNm,place,star,overview,date)

    }
    fun add(
        reviewId:String,
        UserID:String, movieCode: Int, movieNm:String,
        place:String,
        star: Float, overview:String, date:String) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val call = reviewService.addReview(reviewId,UserID,movieCode,movieNm,star,place,overview,date)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    state.postValue(call.body()!!.success)
                    Log.e(TAG, "add reveiw call is Successed : ${call.body()}")
                } else {
                    state.postValue(false)
                    Log.e(TAG, "add reveiw Failed : ${call.raw()}")
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