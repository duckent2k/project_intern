package com.example.myshoppal.activities.ui.viewmodels

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.myshoppal.firestore.FireStoreClass
import com.google.firebase.auth.FirebaseAuth



class LoginViewModel: BaseViewModel() {

    val showErrorSnackBar = MutableLiveData<Boolean>()
    val showProgressDialog = MutableLiveData<Boolean>()

    fun logInRegisteredUser(activity: AppCompatActivity, email: String, passWord: String) {
        val email = email.trim { it <= ' ' }
        val password = passWord.trim { it <= ' ' }

        if (validateLoginDetails(email, password)) {
            showProgressDialog.value = true
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email ?: "", password ?: "")
                .addOnCompleteListener { task ->
                    showProgressDialog.value = false
                    if (task.isSuccessful) {
                        FireStoreClass().getUserDetails(activity)
                    } else {
                        showProgressDialog.value = false
                        showErrorSnackBar.value = true
                    }
                }
        }
    }



    private fun validateLoginDetails(etEmail:String?, etPassword: String?): Boolean {
        return when {
            TextUtils.isEmpty(etEmail?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = true
                false
            }
            TextUtils.isEmpty(etPassword?.trim { it <= ' ' }) -> {
                showErrorSnackBar.value = true
                false
            }
            else -> {
                true
            }

        }
    }


}