package com.difa.difaapp.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}