package com.example.allmyreview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.allmyreview.databinding.RecyclerMovieBinding


class MyMovieAdapter(private var data: ArrayList<MOVIEINFO>) :
    RecyclerView.Adapter<MyMovieAdapter.MyViewHolder>() {

    val TAG = "MyMovieAdapter"

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<MOVIEINFO>) {
        data.clear()
        data.addAll(newMovies)
        notifyDataSetChanged()
    }



    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: RecyclerMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentMovie: MOVIEINFO) {
           // binding.movieinfo =currentMovie
            binding.nameText.text=currentMovie.movieNm
//            binding.executePendingBindings() //데이터가 수정되면 즉각 반영
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //val binding = RecyclerMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerMovieBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //여기~!
        val movie = data
        if (movie != null) {
            holder.bind(movie.get(position))
        }
    }


    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return data.size
//        return data.value?.boxOfficeResult?.dailyBoxOfficeList?.size ?: 10
    }


}