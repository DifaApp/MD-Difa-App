package com.difa.difaapp.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityGetStartedBinding
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.ui.auth.login.LoginActivity

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding
    private val viewModel by viewModels<GetStartedViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()

        binding.btnGetStarted.setOnClickListener {
            viewModel.saveOnboarding(true)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun startAnimation() {
        val tvTitleGetStarted = ObjectAnimator.ofFloat(binding.tvHeadlineGetStarted, View.ALPHA, 1f).setDuration(MILIS)
        val ivGetStarted = ObjectAnimator.ofFloat(binding.ivOnboardingPage1, View.ALPHA, 1f).setDuration(MILIS)
        val btnGetStarted = ObjectAnimator.ofFloat(binding.btnGetStarted, View.ALPHA, 1f).setDuration(MILIS)

        AnimatorSet().apply {
            playSequentially(tvTitleGetStarted, ivGetStarted, btnGetStarted)
            startDelay = 250
        }.start()
    }

    companion object{
        const val MILIS = 500L
    }
}