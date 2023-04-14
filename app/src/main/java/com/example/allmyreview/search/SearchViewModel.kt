package com.example.allmyreview.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.MovieResult
import com.example.allmyreview.MovieRetrofit.RetrofitClient
import kotlinx.coroutines.*

class SearchViewModel : ViewModel() {

    val TAG = "SearchViewModel"
    private val movieService = RetrofitClient.getRetrofitService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    var data = MutableLiveData<List<MovieResult>>()
    var cnt = MutableLiveData<String>()
    val movieLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(keyword: String) {
        getMovieData(keyword)
    }

    private fun getMovieData(keyword: String) {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = movieService.searchMovie(keyword)
//            var response = movieService.getMovie()
            withContext(Dispatchers.Main) {
                Log.d(TAG, "${keyword}:" + response.raw().toString())
                Log.d("SearchViewModel","getMovieData() called")
                if (response.isSuccessful) {
                    data.postValue(response.body()?.results)
                    cnt.postValue(response.body()?.total_results.toString())
                    movieLoadError.postValue(null)
                    loading.postValue(false)

                } else {
                    onError("Error: ${response.message()}")
                    // Log.d(TAG, "Movie failed")
                }
            }
        }

    }

    private fun onError(message: String) {
        movieLoadError.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
