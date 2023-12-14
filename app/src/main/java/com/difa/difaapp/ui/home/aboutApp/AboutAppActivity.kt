package com.difa.difaapp.ui.home.aboutApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityAboutAppBinding

class AboutAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackAboutApp.setOnClickListener {
            finish()
        }
    }
}