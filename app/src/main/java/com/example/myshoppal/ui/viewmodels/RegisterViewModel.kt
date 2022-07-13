package com.example.myshoppal.ui.viewmodels

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.myshoppal.R
import com.example.myshoppal.activities.RegisterActivity
import com.example.myshoppal.firestore.FireStoreClass
import com.example.myshoppal.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel : BaseViewModel() {
    //    val firstName = MutableLiveData<String>().apply { "" }
//    val lastName = MutableLiveData<String>().apply { "" }
//    val email = MutableLiveData<String>().apply { "" }
//    val passWord = MutableLiveData<String>().apply { "" }
//    val confirmPassWord = MutableLiveData<String>().apply { "" }
    val showErrorSnackBar = MutableLiveData<Int>()
    val showProgressDialog = MutableLiveData<Boolean>()

    private fun validateRegisterDetails(
        etFirstName: String?,
        etLastName: String?,
        etEmail: String?,
        etPassword: String?,
        etConfirmPassword: String?
    ): Boolean {
        return when {
            TextUtils.isEmpty(etFirstName?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = 1
                false
            }

            TextUtils.isEmpty(etLastName?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = 1
                false
            }

            TextUtils.isEmpty(etEmail?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = 1
                false
            }

            TextUtils.isEmpty(etPassword?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = 1
                false
            }

            TextUtils.isEmpty(etConfirmPassword?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = 1
                false
            }

            etPassword?.trim { it <= ' ' } != etConfirmPassword?.trim { it <= ' ' } -> {
                showErrorSnackBar.value = 2
                false
            }

            else -> {
                true
            }
        }
    }

    fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmpassword: String,
        activity: RegisterActivity
    ) {
//            val firstname = firstName.value?.trim{ it <= ' '}
//            val lastname = lastName.value?.trim{ it <= ' '}
//            val email =  email.value?.trim { it <= ' ' }
//            val password = passWord.value?.trim { it <= ' ' }
//            val confirmpassword = confirmPassWord.value?.trim { it <= ' ' }

        if (validateRegisterDetails(
                firstname,
                lastname,
                email,
                password,
                confirmpassword
            )
        ) { // pass validate
            showProgressDialog.value = true
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email ?: "", password ?: "")
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        showProgressDialog.value = true

                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                firstname.trim { it <= ' ' },
                                lastname.trim { it <= ' ' },
                                email.trim { it <= ' ' },
                            )
                            FireStoreClass().registerUser(activity, user)
                        } else {
                            showProgressDialog.value = false
                            showErrorSnackBar.value = 1
                        }
                    })
        }
    }

}