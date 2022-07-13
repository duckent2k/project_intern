package com.example.myshoppal.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myshoppal.databinding.ActivityUserProfileBinding

class UserProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnListFriends.setOnClickListener{
            val intent = Intent(this@UserProfileActivity, ListFriendsActivity::class.java)
            startActivity(intent)
        }
    }


}