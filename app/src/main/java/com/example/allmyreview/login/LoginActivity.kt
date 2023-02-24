package com.example.allmyreview.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginlViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginlViewModel::class.java]


    }

    fun gotoSignUp(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun loginBtn(view: View) {
        val email=binding.loginEmail.text.toString()
        val password=binding.loginPassword.text.toString()

        loginViewModel.refresh(email,password)

        loginViewModel.state.observe(this, Observer{
            it.let {
                if(it.UserEmail!=null){
                    val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
                    val autoLoginEdit = auto.edit()
                    autoLoginEdit.putString("userEmail", it.UserEmail)
                    autoLoginEdit.putString("password", it.UserPwd)
                    autoLoginEdit.putString("userName", it.UserName)
                    autoLoginEdit.apply()

                    Toast.makeText(this,"로그인 성공!",Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    binding.loginFailTextview.visibility=View.VISIBLE
                }
            }
        })
    }
}

