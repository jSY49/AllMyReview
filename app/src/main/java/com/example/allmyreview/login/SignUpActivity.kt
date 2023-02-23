package com.example.allmyreview.login

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allmyreview.R
import com.example.allmyreview.databinding.ActivitySignupBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpViewModel: SignUplViewModel
    var EmailCheck = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signUpViewModel = ViewModelProvider(this)[SignUplViewModel::class.java]


        binding.checkButton.setOnClickListener {
            checkEmail(binding.joinEmail.text.toString())
        }

        binding.joinButton.setOnClickListener {
            register()
        }
    }

    fun register() {
        val name = binding.joinName.text.toString()
        val pw = binding.joinPassword.text.toString()
        val checkPw = binding.joinPwck.text.toString()
        val email = binding.joinEmail.text.toString()

        if (name.isBlank() || pw.isBlank() || checkPw.isBlank() || email.isBlank()) {
            Toast.makeText(this, "모든 정보를 입력해 주세요.", Toast.LENGTH_LONG).show()
        } else {
            if (!EmailCheck) {
                Toast.makeText(this, "이메일 확인을 진행해 주세요.", Toast.LENGTH_LONG).show()
            } else {
                if (pw != checkPw) {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                } else {
                    signUpViewModel.refresh(name, pw, email)
                    signUpViewModel.state.observe(this, Observer {
                        it?.let {
                            if (signUpViewModel.state.value == true) {
                                Toast.makeText(this, "회원가입 되었습니다.", Toast.LENGTH_LONG).show()
                                finish()
                            } else {
                                Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                    })

                }
            }
        }
    }

    fun checkEmail(email: String) {
        // 검사 정규식
        val emailValidation =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 검사
        if (!p) {
            binding.emailRes.setTextColor(Color.RED)
            binding.emailRes.text = "이메일 형식이 아닙니다."
        } else {
            signUpViewModel.checkEmail(email)
            signUpViewModel.emailCheck.observe(this, Observer {
                it?.let {
                    if (it) {
                        binding.emailRes.setTextColor(Color.BLUE)
                        binding.emailRes.text = "사용할 수 있는 이메일 입니다."
                        binding.checkButton.setBackgroundColor(Color.GRAY)
                    } else {
                        binding.emailRes.setTextColor(Color.RED)
                        binding.emailRes.text = "사용할 수 없는 이메일 입니다."
                        binding.checkButton.setBackgroundColor(resources.getColor(R.color.purpleBlue))
                    }
                }
            })
        }


    }

    fun cancelBtn(view: View) {
        finish()
    }
}