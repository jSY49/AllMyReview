package com.example.allmyreview

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MOVIES(val boxOfficeResult: BOXOFFICERESULT)
data class BOXOFFICERESULT(
    @SerializedName("boxofficeType") @Expose val boxofficeType: String,
    @SerializedName("showRange") @Expose val showRange: String,
    @SerializedName("dailyBoxOfficeList") @Expose val dailyBoxOfficeList: List<MOVIEINFO>
)
data class MOVIEINFO(
    @SerializedName("rnum") @Expose var rnum: String,
    @SerializedName("rank") @Expose var rank: String,
    @SerializedName("rankInten") @Expose var rankInten: String,
    @SerializedName("rankOldAndNew") @Expose var rankOldAndNew: String,
    @SerializedName("movieCd") @Expose var movieCd: String,
    @SerializedName("movieNm") @Expose var movieNm: String,
    @SerializedName("openDt") @Expose var openDt: String,
    @SerializedName("salesAmt") @Expose var salesAmt: String,
    @SerializedName("salesShare") @Expose var salesShare: String,
    @SerializedName("salesInten") @Expose var salesInten: String,
    @SerializedName("salesChange") @Expose var salesChange: String,
    @SerializedName("salesAcc") var salesAcc: String,
    @SerializedName("audiCnt") @Expose var audiCnt: String,
    @SerializedName("audiInten") @Expose var audiInten: String,
    @SerializedName("audiChange") @Expose var audiChange: String,
    @SerializedName("audiAcc") @Expose var audiAcc: String,
    @SerializedName("scrnCnt") @Expose var scrnCnt: String,
    @SerializedName("showCnt") @Expose var showCnt: String
)


/*data class DAILYBOXOFFICE(
    val audiAcc: String,
    val audiChange: String,
    val audiCnt: String,
    val audiInten: String,
    val movieCd: String,
    val movieNm: String,
    val openDt: String,
    val rank: String,
    val rankInten: String,
    val rankOldAndNew: String,
    val rnum: String,
    val salesAcc: String,
    val salesAmt: String,
    val salesChange: String,
    val salesInten: String,
    val salesShare: String,
    val scrnCnt: String,
    val showCnt: String
)*/
