package com.example.allmyreview

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//tmdb_ trending
data class MovieDb(
    @SerializedName("page") @Expose var page: Int,
    @SerializedName("results") @Expose var results: List<MovieResult>,
    @SerializedName("total_pages") @Expose var total_pages: Int,
    @SerializedName("total_results") @Expose var total_results: Int
)

data class MovieResult(
    @SerializedName("adult") @Expose var adult: Boolean,
    @SerializedName("backdrop_path") @Expose var backdrop_path: String,
    @SerializedName("id") @Expose var id: Int,
    @SerializedName("title") @Expose var title: String,
    @SerializedName("original_language") @Expose var original_language: String,
    @SerializedName("original_title") @Expose var original_title: String,
    @SerializedName("overview") @Expose var overview: String,
    @SerializedName("poster_path") @Expose var poster_path: String,
    @SerializedName("media_type") @Expose var media_type: String,
    @SerializedName("genre_ids") @Expose var genre_ids: List<Int>,
    @SerializedName("popularity") @Expose var popularity: Number,
    @SerializedName("release_date") @Expose var release_date: String,
    @SerializedName("video") @Expose var video: Boolean,
    @SerializedName("vote_average") @Expose var vote_average: Number,
    @SerializedName("vote_count") @Expose var vote_count: Int,

    )

//tmdb_upcoming
data class upcomingMovieDb(
    @SerializedName("dates") @Expose var dates: upComingDate,
    @SerializedName("page") @Expose var page: Int,
    @SerializedName("results") @Expose var results: List<MovieResult2>,
    @SerializedName("total_pages") @Expose var total_pages: Int,
    @SerializedName("total_results") @Expose var total_results: Int
)

data class upComingDate(
    @SerializedName("maximum") @Expose var maximum: String,
    @SerializedName("minimum") @Expose var minimum: String
)

data class MovieResult2(
    @SerializedName("adult") @Expose var adult: Boolean,
    @SerializedName("backdrop_path") @Expose var backdrop_path: String,
    @SerializedName("genre_ids") @Expose var genre_ids: List<Int>,
    @SerializedName("id") @Expose var id: Int,
    @SerializedName("original_language") @Expose var original_language: String,
    @SerializedName("original_title") @Expose var original_title: String,
    @SerializedName("overview") @Expose var overview: String,
    @SerializedName("popularity") @Expose var popularity: Number,
    @SerializedName("poster_path") @Expose var poster_path: String,
    @SerializedName("release_date") @Expose var release_date: String,
    @SerializedName("title") @Expose var title: String,
    @SerializedName("video") @Expose var video: Boolean,
    @SerializedName("vote_average") @Expose var vote_average: Number,
    @SerializedName("vote_count") @Expose var vote_count: Int

)

//tmdb_detail
data class DetailMovie(
    @SerializedName("adult") @Expose var adult: Boolean,
    @SerializedName("backdrop_path") @Expose var backdrop_path: String,
    @SerializedName("belongs_to_collection") @Expose var belongs_to_collection: DetailCollection,
    @SerializedName("budget") @Expose var budget: Int,
    @SerializedName("genres") @Expose var genres: List<Genres>,
    @SerializedName("homepage") @Expose var homepage: String,
    @SerializedName("id") @Expose var id: Int,
    @SerializedName("imdb_id") @Expose var imdb_id: String,
    @SerializedName("original_language") @Expose var original_language: String,
    @SerializedName("original_title") @Expose var original_title: String,
    @SerializedName("overview") @Expose var overview: String,
    @SerializedName("popularity") @Expose var popularity: Number,
    @SerializedName("poster_path") @Expose var poster_path: String,
    @SerializedName("production_companies") @Expose var production_companies: List<Companies>,
    @SerializedName("production_countries") @Expose var production_countries: List<Countries>,
    @SerializedName("release_date") @Expose var release_date: String,
    @SerializedName("revenue") @Expose var revenue: Int,
    @SerializedName("runtime") @Expose var runtime: Int,
    @SerializedName("spoken_languages") @Expose var spoken_languages: List<SpokenLanguag>,
    @SerializedName("status") @Expose var status: String,
    @SerializedName("tagline") @Expose var tagline: String,
    @SerializedName("title") @Expose var title: String,
    @SerializedName("video") @Expose var video: Boolean,
    @SerializedName("vote_average") @Expose var vote_average: Number,
    @SerializedName("vote_count") @Expose var vote_count: Number

)

data class DetailCollection(
    @SerializedName("id") @Expose var id: Int,
    @SerializedName("name") @Expose var name: String,
    @SerializedName("poster_path") @Expose var poster_path: String,
    @SerializedName("backdrop_path") @Expose var backdrop_path: String
)

data class Genres(
    @SerializedName("id") @Expose var id: Int,
    @SerializedName("name") @Expose var name: String
)

data class Companies(
    @SerializedName("id") @Expose var id: Int,
    @SerializedName("logo_path") @Expose var logo_path: String,
    @SerializedName("name") @Expose var name: String,
    @SerializedName("origin_country") @Expose var origin_country: String,
)

data class Countries(
    @SerializedName("iso_3166_1") @Expose var iso_3166_1: String,
    @SerializedName("name") @Expose var name: String
)

data class SpokenLanguag(
    @SerializedName("english_name") @Expose var english_name: String,
    @SerializedName("iso_639_1") @Expose var iso_639_1: String,
    @SerializedName("name") @Expose var name: String
)