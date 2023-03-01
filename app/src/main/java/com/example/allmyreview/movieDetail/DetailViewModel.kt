package com.example.allmyreview.movieDetail

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.DetailMovie
import com.example.allmyreview.MovieRetrofit.RetrofitClient
import com.example.allmyreview.ReviewDb
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import kotlinx.coroutines.*

class DetailViewModel :ViewModel() {

    val TAG = "DetailViewModel"
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    private val DetailService = RetrofitClient.getRetrofitService_Detail()
    private val ReviewService = ReviewRetrofitClient.getRetrofitService()
    var data = MutableLiveData<DetailMovie>()
    var review = MutableLiveData<ReviewDb>()


    fun refresh(id : Int,userId: String?) {
        getMovieDetail(id)
        getReview(id,userId)
    }
    private fun getMovieDetail(id: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = DetailService.getMovieDetail(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                } else {
                    Log.e(TAG, "Error: ${response.message()}")

                }
            }
        }
    }

    private fun getReview(code: Int,userId: String?) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ReviewService.getReview(code,userId)
            Log.d(TAG, response.raw().toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    review.postValue(response.body())
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.e(TAG, "Error: ${response.message()}")

                }
            }
        }
    }
}