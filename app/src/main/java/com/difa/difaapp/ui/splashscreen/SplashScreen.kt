package com.difa.difaapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivitySplashScreenBinding
import com.difa.difaapp.ui.MainActivity
import com.difa.difaapp.ui.auth.login.LoginActivity
import com.difa.difaapp.ui.onboarding.OnboardingActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this@SplashScreen, OnboardingActivity::class.java))
                finish()
            }, DELAY_MILIS
        )
    }

    companion object{
        const val DELAY_MILIS = 1500L
    }
}