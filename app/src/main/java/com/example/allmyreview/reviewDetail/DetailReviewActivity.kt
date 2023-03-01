package com.example.allmyreview.reviewDetail

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivityDetailReviewBinding

class DetailReviewActivity : AppCompatActivity() {
    val TAG = "DetailReviewActivity"
    private lateinit var binding: ActivityDetailReviewBinding
    private lateinit var detailReviewViewModel: DetailReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailReviewViewModel = ViewModelProvider(this)[DetailReviewViewModel::class.java]

        val url= intent.getStringExtra("img")
        val movieId= intent.getIntExtra("movieId", 0)
        val movieNm= intent.getStringExtra("movieNm")
        val auto: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val userid = auto.getString("userID", null).toString()

        detailReviewViewModel.refresh(movieId,userid)
        Log.d(TAG,"$movieId , $userid")
        observe(movieNm,url)
    }
    fun observe(movieNm: String?, url: String?) {
        detailReviewViewModel.review.observe(this, Observer {
            it.let {
                val reviewData = detailReviewViewModel.review.value
                if (reviewData != null) {
                    Glide.with(this).load(url)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .centerCrop()
                        .into(binding.movieImageView)
                    binding.movieNameText.text = movieNm
                    binding.placeTextView.text = it.Review[0].place
                    binding.timeTextView.text = it.Review[0].date
                    binding.reviewTextView.text = it.Review[0].overview
                }
            }
        })
    }
}