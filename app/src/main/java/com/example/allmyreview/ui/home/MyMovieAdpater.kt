package com.example.allmyreview

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.allmyreview.databinding.RecyclerMovieBinding
import com.example.allmyreview.movieDetail.MovieDetailActivity


class MyMovieAdapter(private var data: ArrayList<MovieResult>) :
    RecyclerView.Adapter<MyMovieAdapter.MyViewHolder>() {

    val TAG = "MyMovieAdapter"

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<MovieResult>) {
        data.clear()
        data.addAll(newMovies)
        notifyDataSetChanged()
    }

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: RecyclerMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(currentMovie: MovieResult) {
            binding.numberText.text=(position+1).toString()
            binding.nameText.text=currentMovie.title
            val url ="https://image.tmdb.org/t/p/original"+currentMovie.poster_path
            Glide.with(context).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
                .into(binding.imageView)
//            binding.executePendingBindings() //데이터가 수정되면 즉각 반영

            itemView.setOnClickListener{
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movieId",currentMovie.id)
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
    }


}