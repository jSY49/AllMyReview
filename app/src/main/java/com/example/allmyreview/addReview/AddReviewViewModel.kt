package com.example.allmyreview.addReview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class AddReviewViewModel :ViewModel() {

    val TAG="AddReviewViewModel"
    var job: Job? = null
    var date = getdate()
    fun add(id: Int, overview: String, place: String, applicationContext: Context) {

    }


    @SuppressLint("SimpleDateFormat")
    fun getdate(): String {
        val calendar: Calendar = GregorianCalendar()
        val SDF: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        SDF.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val res = SDF.format(calendar.time)
        Log.d(TAG,res)
        return res
    }

}