package com.example.allmyreview.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allmyreview.MovieResult
import com.example.allmyreview.MyMovieAdapter
import com.example.allmyreview.MyMovieSearchAdpater
import com.example.allmyreview.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding :ActivitySearchBinding
    private var myMovieAdpater = MyMovieSearchAdpater(arrayListOf())
    private lateinit var searchViewModel: SearchViewModel
    var page =1
    var totalPage =1
    var keyword =""
    var totalCnt=0
    var list = ArrayList<MovieResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observe()
        binding.searchBtn.setOnClickListener {
            page=1
            totalPage=1
            list.clear()
            getItem()
        }

        binding.serachEditText.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KEYCODE_ENTER)) {
                binding.serachEditText.clearFocus()
                binding.serachEditText.requestFocus()
                page=1
                totalPage=1
                list.clear()
                getItem()
            }
            return@setOnKeyListener false

        }

    }

    fun setRecycler(){
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            adapter = myMovieAdpater
        }

        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutManager = binding.searchRecyclerView.layoutManager

                if (!binding.searchRecyclerView.canScrollVertically(1)) {   //최하단에 오면`
                    Log.d("SearchActivity","onScrolled _ page: $page, totalPage: $totalPage")
                    if(page < totalPage){
                        page++
                        getItem_add()
                    }

                }

            }
        })
    }

    fun observe(){
        searchViewModel=ViewModelProvider(this)[SearchViewModel::class.java]
        searchViewModel.data.observe(this, Observer {
            it?.let {
                list.addAll(it)
                myMovieAdpater.updateMovies(list)
                myMovieAdpater.notifyDataSetChanged()
            }

        })

        searchViewModel.cnt.observe(this, Observer {
            it?.let{
                binding.resTextView.text="$it 개의 검색 결과가 있습니다."
                totalCnt=it.toInt()
            }
        })
        searchViewModel.totalPage.observe(this, Observer {
            it?.let{
               totalPage= it
            }
        })
    }

    fun hideKeybord(){
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
    }

    fun getItem(){
        keyword= binding.serachEditText.text.toString()
        if(keyword.isNotBlank()){
            setRecycler()
            searchViewModel.refresh(keyword,page)
            hideKeybord()
        }else{
            Toast.makeText(this,"검색어를 입력해 주세요.",Toast.LENGTH_SHORT).show()
        }
    }
    fun getItem_add(){
        searchViewModel.refresh_again(keyword,page)
    }

}