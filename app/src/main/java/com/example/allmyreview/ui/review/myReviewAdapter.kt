package com.example.allmyreview

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.allmyreview.databinding.RecyclerMovieBinding
import com.example.allmyreview.databinding.RecyclerMyreviewBinding
import com.example.allmyreview.movieDetail.MovieDetailActivity
import com.example.allmyreview.reviewDetail.DetailReviewActivity


class myReviewAdapter(private var data: ArrayList<Review>) :
    RecyclerView.Adapter<myReviewAdapter.MyViewHolder>() {

    val TAG = "myReviewAdapter"

    @SuppressLint("NotifyDataSetChanged")
    fun updateReview(newReview: List<Review>) {
        data.clear()
        data.addAll(newReview)
        notifyDataSetChanged()
    }
    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: RecyclerMyreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(currentMovie: Review) {
            binding.text.text=currentMovie.overview
            binding.moviewNM.text="\"${currentMovie.movieNm} \""
            itemView.setOnClickListener{
                val intent = Intent(context, DetailReviewActivity::class.java)
                intent.putExtra("movieId", currentMovie.Moviecode)
                intent.run{
                    context.startActivity(this)
                }

            }

        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //val binding = RecyclerMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerMyreviewBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //여기~!
        val review = data
        if (review != null) {
            holder.bind(review.get(position))
        }
    }


    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return data.size
    }


}