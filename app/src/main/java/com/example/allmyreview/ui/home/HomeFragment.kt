package com.example.allmyreview.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allmyreview.MyMovieAdapter
import com.example.allmyreview.MyNewMovieAdapter
import com.example.allmyreview.databinding.FragmentHomeBinding
import com.example.allmyreview.login.LoginActivity
import com.example.allmyreview.login.SignUpActivity
import com.example.allmyreview.users.UserActivity


class HomeFragment : Fragment() {
    val TAG = "HomeFragment"
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModel2: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var myMovieAdpater = MyMovieAdapter(arrayListOf())
    private var myMovieAdpater2 = MyNewMovieAdapter(arrayListOf())

    private val binding get() = _binding!!

    lateinit var auto : SharedPreferences
    var userEmail : String?= null
    var userName : String? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auto = this.requireActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        userEmail = auto.getString("userEmail", null)
        userName = auto.getString("userName", null)


        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.refresh("pop")
        binding.popularMovieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = myMovieAdpater
        }
        observeViewModel()


        homeViewModel2 = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel2.refresh("upcoming")
        binding.newMoviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = myMovieAdpater2
        }
        observeViewModel2()


        binding.userButton.setOnClickListener {
            Log.d(TAG,"userEmail ${userEmail}, userName ${userName}")
            if(userEmail!=null&&userName!=null){
                val intent = Intent(context, UserActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }

        }


        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    fun observeViewModel() {
        homeViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                myMovieAdpater.updateMovies(it)
                myMovieAdpater.notifyDataSetChanged()
            }

        })

        homeViewModel.movieLoadError.observe(viewLifecycleOwner, Observer { isError ->
            Log.d(TAG, "movieLoadError=" + isError.toString())
            //Toast.makeText(context,isError,Toast.LENGTH_LONG).show()
        })

        homeViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    Log.d(TAG, "loading=" + it.toString())
                    //Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    fun observeViewModel2() {
        homeViewModel2.data2.observe(viewLifecycleOwner, Observer {
            it?.let {
                myMovieAdpater2.updateMovies(it)
                myMovieAdpater2.notifyDataSetChanged()
                binding.scrollview.visibility= View.VISIBLE
            }

        })
        homeViewModel2.data2.observe(viewLifecycleOwner, Observer {
            it?.let {
                myMovieAdpater2.updateMovies(it)
                myMovieAdpater2.notifyDataSetChanged()
            }

        })

        homeViewModel2.movieLoadError.observe(viewLifecycleOwner, Observer { isError ->
            Log.d(TAG, "movieLoadError=" + isError.toString())
            //Toast.makeText(context,isError,Toast.LENGTH_LONG).show()
        })

        homeViewModel2.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    Log.d(TAG, "loading=" + it.toString())
                    //Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        userEmail = auto.getString("userEmail", null)
        userName = auto.getString("userName", null)
        super.onResume()
    }
}