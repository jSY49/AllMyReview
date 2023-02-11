package com.example.allmyreview


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class apiUrl{

    companion object{
        const val trendingMovie_EndPoint ="trending/movie/week"
        const val newMovie_EndPoint ="movie/upcoming"
        const val TMDB_API_KEY = BuildConfig.TMDB_API

    }

}

interface ApiUrlInterface{
    @GET(apiUrl. trendingMovie_EndPoint)
    suspend fun getMovie(
        @Query("api_key")
        key:String = apiUrl.TMDB_API_KEY,
        @Query("language")
        language:String = "ko-KR",
    ): Response<MovieDb>
}
interface new_ApiUrlInterface{
    @GET(apiUrl. newMovie_EndPoint)
    suspend fun getNewMovie(
        @Query("api_key")
        key:String = apiUrl.TMDB_API_KEY,
        @Query("language")
        language:String = "ko-KR",
    ): Response<upcomingMovieDb>
}
/*
@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun getdate() :String{
    //하루 전 날짜
    val calendar :Calendar = GregorianCalendar()
    val SDF : SimpleDateFormat = SimpleDateFormat("yyyyMMdd")
    calendar.add(Calendar.DATE,-1)
    val res = SDF.format(calendar.time)
    Log.d("getDate()",res )
    return res
}*/
