package com.example.allmyreview.updateReview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allmyreview.databinding.ActivityAddReviewBinding

class updateReviewActivity : AppCompatActivity() {

    val TAG = "updateReviewActivity"
    private lateinit var binding: ActivityAddReviewBinding
    private lateinit var updateReviewViewModel: UpdateReviewViewModel

    var movieId = 0
    var userid = ""
    var movieNm = ""
    var place = ""
    var allReview = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateReviewViewModel = ViewModelProvider(this)[UpdateReviewViewModel::class.java]

        userid = intent.getStringExtra("userid").toString()
        movieId = intent.getIntExtra("movieId", 0)
        movieNm = intent.getStringExtra("movieNm").toString()
        place = intent.getStringExtra("place").toString()
        allReview = intent.getStringExtra("review").toString()

        ui()

    }

    fun ui() {
        binding.movieNameText.setText(movieNm)
        binding.placeEditText.setText(place)
        binding.reviewEditText.setText(allReview)
    }

    fun addReviewBtn(view: View) {
        val review = binding.reviewEditText.text.toString()
        val p = binding.placeEditText.text.toString()
        Log.d(TAG, "$review , $p")
        Log.d(TAG, "${userid + movieId}")
        if (review != "" && p != "") {
            updateReviewViewModel.refresh((userid + movieId.toString()), p, review)
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