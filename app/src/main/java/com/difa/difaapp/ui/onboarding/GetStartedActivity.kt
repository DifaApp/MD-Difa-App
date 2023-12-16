package com.difa.difaapp.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.btnGetStarted.setOnClickListener {
            viewModel.saveOnboarding(true)
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}