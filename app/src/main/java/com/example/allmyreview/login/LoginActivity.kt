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
import com.example.allmyreview.users.UserViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginlViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginlViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]


    }

    fun gotoSignUp(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun loginBtn(view: View) {
        val id=binding.loginId.text.toString()
        val password=binding.loginPassword.text.toString()

        loginViewModel.refresh(id,password)

        loginViewModel.state.observe(this, Observer{
            it.let {
                if(it.UserID!=null){
                    userViewModel.addInfo(this,it.UserID,it.UserEmail , it.UserName,it.UserPwd)
                    Toast.makeText(this,"로그인 성공!",Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    binding.loginFailTextview.visibility=View.VISIBLE
                }
            }
        })
    }
}

