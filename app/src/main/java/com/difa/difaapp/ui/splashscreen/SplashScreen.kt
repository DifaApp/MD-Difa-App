package com.difa.difaapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivitySplashScreenBinding
import com.difa.difaapp.ui.MainActivity
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.ui.auth.login.LoginActivity
import com.difa.difaapp.ui.onboarding.OnboardingActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isOnboarding().observe(this, Observer {onBoarding->
            when(onBoarding){
                true -> {
                    viewModel.isLoginGoogle().observe(this, Observer { isGoogleAccount->
                        if(isGoogleAccount){
                            viewModel.getSessionGoogleUser().observe(this, Observer {googleAccount ->
                                if(!googleAccount.name.isEmpty()){
                                    Handler(Looper.getMainLooper()).postDelayed(
                                        {
                                            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                                            finish()
                                        }, DELAY_MILIS
                                    )
                                }else {
                                    Handler(Looper.getMainLooper()).postDelayed(
                                        {
                                            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                                            finish()
                                        }, DELAY_MILIS
                                    )
                                }
                            })
                        }else {
                            viewModel.getSessionNormalUser().observe(this, Observer { normalUser->
                                if(!normalUser.token.isEmpty()){
                                    Handler(Looper.getMainLooper()).postDelayed(
                                        {
                                            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                                            finish()
                                        }, DELAY_MILIS
                                    )
                                }else {
                                    Handler(Looper.getMainLooper()).postDelayed(
                                        {
                                            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                                            finish()
                                        }, DELAY_MILIS
                                    )
                                }
                            })
                        }
                    })
                }
                false -> {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            startActivity(Intent(this@SplashScreen, OnboardingActivity::class.java))
                            finish()
                        }, DELAY_MILIS
                    )
                }
            }
        })
    }

    companion object{
        const val DELAY_MILIS = 1500L
        private const val TAG = "SplashScreen"
    }
}