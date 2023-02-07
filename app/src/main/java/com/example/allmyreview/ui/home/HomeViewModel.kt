package com.example.allmyreview.ui.home

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.allmyreview.ApiUrlInterface
import com.example.allmyreview.MOVIEINFO
import com.example.allmyreview.MOVIES
import com.example.allmyreview.RetrofitClient
import kotlinx.coroutines.*
import retrofit2.*
import java.text.SimpleDateFormat
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel : ViewModel() {

    val TAG="HomeViewModel"
    private val movieService = RetrofitClient.getRetrofitService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var data = MutableLiveData<List<MOVIEINFO>>()
    val movieLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        getMovieData()
    }

    private fun getMovieData(){
        loading.value=true

        job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = movieService.getDailyBoxOffice(getdate())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    data.postValue(response.body()?.boxOfficeResult?.dailyBoxOfficeList)
                    Log.d(TAG, response.body()?.boxOfficeResult?.dailyBoxOfficeList.toString())
                    movieLoadError.postValue(null)
                    loading.postValue(false)
//                    movieLoadError.value?.let { Log.e(TAG, it) }
//                    loading.value?.let { Log.e(TAG, it.toString()) }
                } else {
                    onError("Error : ${response.message()}")
//                    movieLoadError.value?.let { Log.e(TAG, it) }
//                    loading.value?.let { Log.e(TAG, it.toString()) }
                }
            }
        }


        /*movieService.getDailyBoxOffice(getdate()).enqueue(object : retrofit2.Callback<MOVIES> {
            // 통신 성공
            override fun onResponse(call: Call<MOVIES>, response: Response<MOVIES>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        data.value=body.boxOfficeResult.dailyBoxOfficeList
                    }
                    Log.d("Retrofit onResponse", response.toString())
                    //movieList_size = data.value!!.boxOfficeResult.dailyBoxOfficeList.size
                    //Log.d(TAG, movieList_size.toString())
                    //Log.d("Retrofit onResponse", data.value.toString())
                    //Log.d("Retrofit onResponse", response.body().toString() )
                }
            }
            // 통신 실패
            override fun onFailure(call: Call<MOVIES>, t: Throwable) {
                Log.e("Retrofit onFailure",t.toString())
                call.cancel()
            }
        })
    return data*/
    }
//    @JvmName("getData1")
//    fun getData(): MutableLiveData<List<MOVIEINFO>>{
//        return data
//    }
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getdate() :String{
        //하루 전 날짜
        val calendar : Calendar = GregorianCalendar()
        val SDF : SimpleDateFormat = SimpleDateFormat("yyyyMMdd")
        calendar.add(Calendar.DATE,-1)
        val res = SDF.format(calendar.time)
        Log.d("getDate()",res )
        return res
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




