package com.example.allmyreview.users

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.allmyreview.R

class UserActivity : AppCompatActivity() {
    lateinit var auto : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }

    fun logoutBtn(view: View) {
        auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
        auto.edit().clear().apply()

        finish()
    }
}