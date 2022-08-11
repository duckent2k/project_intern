package com.example.myshoppal.activities.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myshoppal.activities.ui.viewmodels.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.myshoppal.R
import com.myshoppal.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()


        binding.btnRegister.setOnClickListener {
            val firstname = binding.etFirstName.text.toString()
            val lastname = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            viewModel.registerUser(
                firstname,
                lastname,
                email,
                password,
                confirmPassword,
                this@RegisterActivity
            )
        }

        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }
        checkData()
    }

  private fun checkData() {
      viewModel.showErrorSnackBar.observe(this, Observer { it ->
          if (it == 1) {
              showErrorSnackBar("Fill enought information!!", true)
          } else if (it == 2) {
              showErrorSnackBar("Password wrong!!", true)
          }
      })
  }

    fun userRegistrationSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@RegisterActivity,
            getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_color_black_24dp)
        binding.toolbarRegisterActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}

