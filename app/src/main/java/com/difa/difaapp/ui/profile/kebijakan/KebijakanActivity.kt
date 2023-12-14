package com.difa.difaapp.ui.profile.kebijakan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityKebijakanBinding

class KebijakanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKebijakanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKebijakanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackPrivacy.setOnClickListener {
            finish()
        }
    }
}