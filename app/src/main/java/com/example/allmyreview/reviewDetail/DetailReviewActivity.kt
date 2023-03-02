package com.example.allmyreview.reviewDetail

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivityDetailReviewBinding
import com.example.allmyreview.updateReview.updateReviewActivity

class DetailReviewActivity : AppCompatActivity() {
    val TAG = "DetailReviewActivity"
    private lateinit var binding: ActivityDetailReviewBinding
    private lateinit var detailReviewViewModel: DetailReviewViewModel
    var movieId =0
    var userid =""
    var movieNm =""
    var place =""
    var allReview =""
    var url=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailReviewViewModel = ViewModelProvider(this)[DetailReviewViewModel::class.java]

        url= intent.getStringExtra("img").toString()
        movieId= intent.getIntExtra("movieId", 0)
        movieNm= intent.getStringExtra("movieNm").toString()
        val auto: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        userid = auto.getString("userID", null).toString()

        getReview()

    }
    fun getReview(){
        detailReviewViewModel.refresh(movieId,userid)
        Log.d(TAG,"$movieId , $userid")
        observe(movieNm,url)
    }
    fun observe(movieNm: String?, url: String?) {
        detailReviewViewModel.review.observe(this, Observer {
            it.let {
                val reviewData = detailReviewViewModel.review.value!!.blank
                if (!reviewData) {
                    Glide.with(this).load(url)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .centerCrop()
                        .into(binding.movieImageView)
                    binding.movieNameText.text = movieNm
                    binding.timeTextView.text = it.Review[0].date

                    binding.placeTextView.text = it.Review[0].place
                    binding.reviewTextView.text = it.Review[0].overview
                    place = it.Review[0].place
                    allReview = it.Review[0].overview
                }
            }
        })
    }

    fun updateBtn(view: View) {
        val intent = Intent(this,updateReviewActivity::class.java)
        intent.putExtra("movieId",movieId)
        intent.putExtra("userid",userid)
        intent.putExtra("movieNm",movieNm)
        intent.putExtra("place",place)
        intent.putExtra("review",allReview)
        startActivity(intent)
    }
    fun deleteBtn(view: View) {

    }

    override fun onResume() {
        super.onResume()
        getReview()
    }
}