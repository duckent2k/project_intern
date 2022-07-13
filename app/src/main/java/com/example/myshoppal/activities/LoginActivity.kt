package com.example.myshoppal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myshoppal.R
import com.example.myshoppal.databinding.ActivityLoginBinding
import com.example.myshoppal.models.User
import com.example.myshoppal.ui.viewmodels.LoginViewModel


class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        subscribeData()
    }


    private fun subscribeData() {
        viewModel.showProgressDialog.observe(this, Observer { it ->
            if (it) {
                showProgressDialog("Please wait")
            } else {
                hideProgressDialog()
            }
        })

        viewModel.showErrorSnackBar.observe(this, Observer { it ->
            if (it) {
                showErrorSnackBar("Error", true)
            }
        })
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {
                    viewModel.logInRegisteredUser(
                        this@LoginActivity,
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                }

                R.id.tv_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }


    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()

        Log.i("First Name:", user.firstname)
        Log.i("Last Name:", user.lastname)
        Log.i("Email:", user.email)

        if (user.profileCompleted == 0) {
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            startActivity(intent)
        } else {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

        finish()
    }
}
