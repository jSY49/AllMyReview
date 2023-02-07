package com.example.allmyreview


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class apiUrl{

    companion object{
        const val EndPoint ="searchDailyBoxOfficeList.json?"
        const val API_KEY = BuildConfig.KOFIC_API
    }

}

interface ApiUrlInterface{
    @GET(apiUrl.EndPoint)
    suspend fun getDailyBoxOffice(
        @Query("targetDt") //parameter
        targetDate:String,
        @Query("key")
        key:String = apiUrl.API_KEY
    ): Response<MOVIES>
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
