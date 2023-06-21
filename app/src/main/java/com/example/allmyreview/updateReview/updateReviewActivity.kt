package com.example.allmyreview.updateReview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allmyreview.databinding.ActivityAddReviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class updateReviewActivity : AppCompatActivity() {

    val TAG = "updateReviewActivity"
    private lateinit var binding: ActivityAddReviewBinding
    private val updateReviewViewModel: UpdateReviewViewModel by viewModels()

    var movieId = 0
    var userid = ""
    var movieNm = ""
    var place = ""
    var star = 0.0f
    var allReview = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        updateReviewViewModel = ViewModelProvider(this)[UpdateReviewViewModel::class.java]

        userid = intent.getStringExtra("userid").toString()
        movieId = intent.getIntExtra("movieId", 0)
        movieNm = intent.getStringExtra("movieNm").toString()
        place = intent.getStringExtra("place").toString()
        star = intent.getFloatExtra("star",0.0f)
        allReview = intent.getStringExtra("review").toString()
        Log.d("updateReviewActivity","onCreate() called - $star")
        ui()

    }

    fun ui() {
        binding.movieNameText.setText(movieNm)
        binding.placeEditText.setText(place)
        binding.reviewEditText.setText(allReview)
        binding.ratingBar.rating=star
    }

    fun addReviewBtn(view: View) {
        val review = binding.reviewEditText.text.toString()
        val p = binding.placeEditText.text.toString()
        var s =  binding.ratingBar.rating
        Log.d(TAG, "$review , $p")
        Log.d(TAG, "${userid + movieId}")
        if (review != "" && p != "") {
            updateReviewViewModel.refresh((userid + movieId.toString()), p, review,s)
            observe()
        }

    }

    fun observe() {
        updateReviewViewModel.state.observe(this, Observer {
            it.let {
                if(it)
                    finish()
            }
        })
    }

}