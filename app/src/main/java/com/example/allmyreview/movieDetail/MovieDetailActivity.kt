package com.example.allmyreview.movieDetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.allmyreview.Genres
import com.example.allmyreview.R
import com.example.allmyreview.addReview.AddReviewActivity
import com.example.allmyreview.databinding.ActivityMovieDetailBinding
import com.example.allmyreview.reviewDetail.DetailReviewActivity
import kotlinx.coroutines.*

class MovieDetailActivity : AppCompatActivity() {

    private val TAG = "MovieDetailActivity"
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var id = 0
    private var MovieName = ""
    var reviewCheck=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auto: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val userid =auto.getString("userID", null)
        id = intent.getIntExtra("movieId", 0)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.refresh(id,userid)
        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    fun observeViewModel() {

        detailViewModel.data.observe(this, Observer {
            it?.let {
                val detailData = detailViewModel.data
//                Log.d(TAG, "movie detail Data = " + detailData.value)
                binding.movieTitle.text = "\"" + detailData.value!!.title + "\""
                MovieName = detailData.value!!.title
                binding.movieOriginalTitle.text = detailData.value!!.original_title
                val url = "https://image.tmdb.org/t/p/original" + detailData.value!!.backdrop_path
                Glide.with(this).load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop()
                    .into(binding.moviePoster)
                binding.movieTagLine.text =
                    if (detailData.value?.tagline.equals("")) "공개된 tagline이 없어요 :-(" else detailData.value?.tagline
                binding.movieOverview.text =
                    if (detailData.value?.overview.equals("")) "공개된 소개글이 없어요 :-(" else detailData.value?.overview

                binding.movieReleaseDate.text = detailData.value?.release_date
                binding.movieGenres.text = getGenres(detailData.value!!.genres)
                binding.movieRunnigTime.text = detailData.value!!.runtime.toString() + "분"

                binding.scrollview.visibility = View.VISIBLE
            }

        })

        detailViewModel.review.observe(this,Observer{
            it?.let {
                val reviewData = detailViewModel.review.value
                if (reviewData != null) {
                    reviewCheck=true
                    binding.reviewText.text=reviewData.Review.get(0).overview
                    binding.gotowriteBtn.visibility=View.GONE
                }
            }
        })

    }

    fun getGenres(genres: List<Genres>): String {
        var str = ""
        for (i in 0 until genres.size)
            str += (genres.get(i).name + " ")
        return str
    }

    fun addReviewBtn(view: View) {
        val intent = Intent(this, AddReviewActivity::class.java)
        intent.putExtra("movieId", id)
        intent.putExtra("movieName", MovieName)
        startActivity(intent)
    }

    fun gotoDetailReview(view: View) {

    }

}