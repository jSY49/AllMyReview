package com.example.allmyreview.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allmyreview.MyMovieAdapter
import com.example.allmyreview.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    val TAG = "HomeFragment"
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var myMovieAdpater=MyMovieAdapter(arrayListOf())

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.refresh()

        binding.popularMoviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context).also {
                it.orientation=LinearLayoutManager.HORIZONTAL
            }
            adapter = myMovieAdpater
        }

        observeViewModel()


        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun observeViewModel() {
        homeViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let{
                myMovieAdpater.updateMovies(it)
                myMovieAdpater.notifyDataSetChanged()
            }

        })

        homeViewModel.movieLoadError.observe( viewLifecycleOwner, Observer { isError ->
            Log.d(TAG,"movieLoadError="+isError.toString())
            //Toast.makeText(context,isError,Toast.LENGTH_LONG).show()
        })

        homeViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if(it){
                    Log.d(TAG,"loading="+it.toString())
                    //Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        })
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}