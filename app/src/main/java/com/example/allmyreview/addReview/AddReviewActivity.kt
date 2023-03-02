package com.example.allmyreview.addReview

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allmyreview.databinding.ActivityAddReviewBinding

class AddReviewActivity : AppCompatActivity() {

    val TAG="AddReviewActivity"
    private lateinit var binding: ActivityAddReviewBinding
    private var id= 0
    private var movieNm=""
    private lateinit var addReviewViewModel: AddReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addReviewViewModel = ViewModelProvider(this)[AddReviewViewModel::class.java]
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        id= intent.getIntExtra("movieId",0)
        movieNm= intent.getStringExtra("movieName").toString()

        setUI()
    }

    fun setUI(){
        binding.movieNameText.text=movieNm
    }

    fun addReviewBtn(view: View) {
        val auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)

        val userid =auto.getString("userID", null)
        val review=binding.reviewEditText.text.toString()
        val place=binding.placeEditText.text.toString()
        val date = addReviewViewModel.getdate()

        if(review!=""&&place!=""){
            if (userid != null) {
                addReviewViewModel.refresh(userid+id,userid,id,place, review,date)
                addReviewViewModel.state.observe(this, Observer {
                    if(it)
                        finish()
//                    else
//                        Toast.makeText(this,"리뷰 저장에 실패 했습니다.\n다시 시도해 주세요",Toast.LENGTH_LONG).show()
                })
            }
            else{
                Toast.makeText(this,"로그인 먼저 해주세요.",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"내용을 모두 입력해 주세요",Toast.LENGTH_LONG).show()
        }

    }


}