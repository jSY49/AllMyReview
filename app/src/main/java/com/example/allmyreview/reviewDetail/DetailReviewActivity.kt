package com.example.allmyreview.reviewDetail

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivityDetailReviewBinding
import com.example.allmyreview.movieDetail.MovieDetailViewModel
import com.example.allmyreview.updateReview.updateReviewActivity


class DetailReviewActivity : AppCompatActivity() {
    val TAG = "DetailReviewActivity"
    private lateinit var binding: ActivityDetailReviewBinding
    private lateinit var detailReviewViewModel: DetailReviewViewModel
    private lateinit var deleteReviewViewModel: DeleteReviewViewModel

    var movieId =0
    var userid =""
    var movieNm =""
    var place =""
    var star =0.0f
    var allReview =""
    var url=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailReviewViewModel = ViewModelProvider(this)[DetailReviewViewModel::class.java]
        deleteReviewViewModel = ViewModelProvider(this)[DeleteReviewViewModel::class.java]
        movieId= intent.getIntExtra("movieId", 0)

        val auto: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        userid = auto.getString("userID", null).toString()

        observeMData()

    }
    fun observeMData(){
        var detailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        detailViewModel.refresh(movieId)
        detailViewModel.data.observe(this, Observer {
            it?.let {
                url ="https://image.tmdb.org/t/p/original"+it.backdrop_path
                Glide.with(this).load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop()
                    .into(binding.movieImageView)
                movieNm=it.title
                getReview()
            }
        })
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
                    binding.ratingBar.rating=it.Review[0].star
                    place = it.Review[0].place
                    star = it.Review[0].star
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
        intent.putExtra("star",star)
        intent.putExtra("review",allReview)
        startActivity(intent)
    }
    fun deleteBtn(view: View) {
        val dlg = AlertDialog.Builder(this@DetailReviewActivity)
        dlg.setTitle("내 리뷰를 삭제?!")
        dlg.setMessage("정말 리뷰를 삭제하시겠습까..?")
        dlg.setPositiveButton("확인") { dialog, which ->
            deleteReviewViewModel.refresh(userid+movieId)
            observeDelete()
        }
        dlg.show()
    }

    private fun observeDelete() {
        deleteReviewViewModel.state.observe(this, Observer {
            if(it){
                Toast.makeText(this,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"삭제되지 않았습니다.\n다시 시도해주세요.",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getReview()
    }
}