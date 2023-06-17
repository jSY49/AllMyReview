package com.example.allmyreview.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allmyreview.MovieResult
import com.example.allmyreview.MovieResult2
import com.example.allmyreview.MovieRetrofit.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.*
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val TAG = "HomeViewModel"
        private val movieService = RetrofitClient.getRetrofitService()
    private val upcomingMovieService = RetrofitClient.getRetrofitService2()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var data = MutableLiveData<List<MovieResult>>()
    var data2 = MutableLiveData<List<MovieResult2>>()
    val movieLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(want :String) {
        getMovieData(want)

    }

    private fun getMovieData(want: String) {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            when(want){
                "pop" -> {
                    var response = movieService.getMovie()
                    withContext(Dispatchers.Main) {
                        Log.d(TAG, "${want}:"+response.code().toString())
                        Log.d("HomeViewModel",response.raw().toString())
                        if (response.isSuccessful) {
                            data.postValue(response.body()?.results)
                            //Log.d(TAG, response.body()?.results.toString())
                            movieLoadError.postValue(null)
                            loading.postValue(false)

                        } else {
                            onError("Error: ${response.message()}")
                            // Log.d(TAG, "Movie failed")
                        }
                    }
                }
                "upcoming"->{
                    var response = upcomingMovieService.getNewMovie()
                    withContext(Dispatchers.Main) {
                        Log.d(TAG, "${want}:"+response.code().toString())
                        if (response.isSuccessful) {
                            data2.postValue(response.body()?.results)
                            //Log.d(TAG, response.body()?.results.toString())
                            movieLoadError.postValue(null)
                            loading.postValue(false)

                        } else {
                            onError("Error: ${response.message()}")
                            // Log.d(TAG, "Movie failed")
                        }
                    }
                }
                else ->{
                    var response = movieService.getMovie()
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






