package com.difa.difaapp.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityOnboadingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentOnboarding = OnboardingFragment()
        fragmentManager
            .beginTransaction()
            .replace(R.id.frame_onboarding, fragmentOnboarding)
            .commit()
    }
}