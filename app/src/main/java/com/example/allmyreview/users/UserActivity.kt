package com.example.allmyreview.users

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivityUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    lateinit var auto : SharedPreferences
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]


        if(userViewModel.getIdCheck(this)){
            userViewModel.getInfo()
            setUI()
        }


    }

    private fun setUI() {
        userViewModel.UserName.observe(this, Observer {
            binding.UserNm.text=userViewModel.UserName.value+" 님"
        })
        userViewModel.UserEmail.observe(this, Observer {
            binding.UserEmail.text=userViewModel.UserEmail.value
        })
        userViewModel.UserId.observe(this, Observer {
            binding.UserId.text=userViewModel.UserId.value
        })


    }

    fun logoutBtn(view: View) {
        auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
        auto.edit().clear().apply()

        finish()
    }

}