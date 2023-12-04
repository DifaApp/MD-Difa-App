package com.difa.difaapp.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityGetStartedBinding

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}