package com.example.allmyreview.movieDetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.example.allmyreview.Genres
import com.example.allmyreview.R
import com.example.allmyreview.addReview.AddReviewActivity
import com.example.allmyreview.databinding.ActivityMovieDetailBinding
import com.example.allmyreview.reviewDetail.DetailReviewActivity
import com.example.allmyreview.reviewDetail.DetailReviewViewModel
import kotlinx.coroutines.*
import java.text.DecimalFormat

class MovieDetailActivity : AppCompatActivity() {

    private val TAG = "MovieDetailActivity"
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var detailViewModel: MovieDetailViewModel
    private lateinit var reviewViewModel: DetailReviewViewModel
    private var id = 0
    private var userid = ""
    private var MovieName = ""
    var reviewCheck=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auto: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        userid = auto.getString("userID", null).toString()
        id = intent.getIntExtra("movieId", 0)
        detailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        detailViewModel.refresh(id)

        reviewViewModel = ViewModelProvider(this)[DetailReviewViewModel::class.java]

        getReview()

    }

    fun getReview(){
        reviewViewModel.refresh(id,userid)
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

                val df = DecimalFormat("#.#")
                val roundoff = df.format(detailData.value!!.vote_average) // 295.3
                binding.movieStar.text= roundoff.toString()
                binding.scrollview.visibility = View.VISIBLE
            }

        })

        reviewViewModel.review.observe(this,Observer{
            it?.let {
                val reviewData = reviewViewModel.review.value!!.blank
                if (!reviewData) {
                    reviewCheck=true
                    binding.reviewText.text= it.Review[0].overview
                    binding.gotowriteBtn.visibility=View.GONE
                }else{
                    binding.reviewText.text=getString(R.string.reviewIsNone)
                    binding.gotowriteBtn.visibility=View.VISIBLE
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
        val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
        if(!auto.getString("userID",null).isNullOrBlank()){
            val intent = Intent(this, AddReviewActivity::class.java)
            intent.putExtra("movieId", id)
            intent.putExtra("movieName", MovieName)
            startActivity(intent)
        }else{
            Toast.makeText(this,"로그인 후 작성할 수 있습니다.",Toast.LENGTH_SHORT).show()
        }

    }

    fun gotoDetailReview(view: View) {
        if(reviewCheck){
            val intent = Intent(this, DetailReviewActivity::class.java)
            val url = "https://image.tmdb.org/t/p/original" + detailViewModel.data.value!!.backdrop_path
            //intent.putExtra("img", url)
            intent.putExtra("movieId", id)
            //intent.putExtra("movieNm", MovieName)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getReview()
    }
}