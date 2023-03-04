package com.example.allmyreview.ui.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allmyreview.databinding.FragmentReviewBinding
import com.example.allmyreview.movieDetail.MovieDetailViewModel
import com.example.allmyreview.myReviewAdapter

class ReviewFragment : Fragment() {

    val TAG="ReviewFragment"
    lateinit var reviewViewModel: ReviewViewModel
    private var _binding: FragmentReviewBinding? = null
    private var myReviewAdapter = myReviewAdapter(arrayListOf())
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewViewModel = ViewModelProvider(this)[ReviewViewModel::class.java]
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reviewViewModel.refresh(this)
        observeLogin()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun observeLogin(){

        reviewViewModel.loginCheck.observe(viewLifecycleOwner, Observer {
            it?.let{
               if(!it){
                   binding.textUserNone.visibility=View.VISIBLE
               }else{
                   binding.textUserNone.visibility=View.GONE
                   setRecycler()
                   reviewViewModel.setId()
                   observReview()
               }
            }
        })
    }

    fun setRecycler(){
        binding.myReviewRecycler.apply {
            layoutManager=LinearLayoutManager(context).also {
                it.orientation=LinearLayoutManager.VERTICAL
            }
            adapter=myReviewAdapter
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun observReview(){
        reviewViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                myReviewAdapter.updateReview(it)
                myReviewAdapter.notifyDataSetChanged()
            }
        })
    }

}