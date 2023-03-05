package com.example.allmyreview.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allmyreview.MyMovieAdapter
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding :ActivitySearchBinding
    private var myMovieAdpater = MyMovieAdapter(arrayListOf())
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel=ViewModelProvider(this)[SearchViewModel::class.java]

        binding.searchBtn.setOnClickListener {
            val keyword= binding.serachEditText.text.toString()
            if(!keyword.isNullOrBlank()){
                setRecycler()
                searchViewModel.refresh(keyword)
                observe()
            }else{
                Toast.makeText(this,"검색어를 입력해 주세요.",Toast.LENGTH_SHORT).show()
            }

        }



    }

    fun setRecycler(){
        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2).also {
                it.orientation = GridLayoutManager.VERTICAL
            }
            adapter = myMovieAdpater
        }
    }

    fun observe(){
        searchViewModel.data.observe(this, Observer {
            it?.let {
                myMovieAdpater.updateMovies(it)
                myMovieAdpater.notifyDataSetChanged()
            }

        })
    }
}