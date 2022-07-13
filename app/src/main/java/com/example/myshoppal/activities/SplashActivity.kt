package com.example.myshoppal.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.myshoppal.R


class SplashActivity : BaseActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        Handler().postDelayed(
            {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            },
            2500
        )
    }
}


