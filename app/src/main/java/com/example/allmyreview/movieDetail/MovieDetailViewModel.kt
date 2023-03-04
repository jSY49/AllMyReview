package com.example.allmyreview.movieDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.DetailMovie
import com.example.allmyreview.MovieRetrofit.RetrofitClient
import com.example.allmyreview.ReviewDb
import com.example.allmyreview.reviewRetrofit.ReviewRetrofitClient
import kotlinx.coroutines.*

class MovieDetailViewModel :ViewModel() {

    val TAG = "DetailViewModel"
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Exception: ${throwable.localizedMessage}")
    }
    private val DetailService = RetrofitClient.getRetrofitService_Detail()
    var data = MutableLiveData<DetailMovie>()


    fun refresh(id : Int) {
        getMovieDetail(id)
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

}