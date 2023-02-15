package com.example.allmyreview.addReview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.allmyreview.databinding.ActivityAddReviewBinding

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding
    private var id= 0
    private var movieNm=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        id= intent.getIntExtra("movieId",0)
        movieNm= intent.getStringExtra("movieName").toString()

        setUI()

    }

    fun setUI(){
        binding.movieNameText.text=movieNm
    }

    fun addReviewBtn(view: View) {}

}