package com.difa.difaapp.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}