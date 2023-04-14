package com.example.allmyreview.search

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allmyreview.MyMovieAdapter
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
            getItem()
        }

      /*  binding.serachEditText.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.searchBtn.performClick()
                getItem()
                handled = true
            }
            handled
        }*/

        binding.serachEditText.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KEYCODE_ENTER)) {
                binding.serachEditText.clearFocus()
                binding.serachEditText.requestFocus()
                getItem()
            }
            return@setOnKeyListener false

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

        searchViewModel.cnt.observe(this, Observer {
            it?.let{
                binding.resTextView.text="$it 개의 검색 결과가 있습니다."
            }
        })
    }

    fun hideKeybord(){
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
    }

    fun getItem(){
        val keyword= binding.serachEditText.text.toString()
        if(!keyword.isNullOrBlank()){
            setRecycler()
            searchViewModel.refresh(keyword)
            observe()
            hideKeybord()
        }else{
            Toast.makeText(this,"검색어를 입력해 주세요.",Toast.LENGTH_SHORT).show()
        }
    }
}