package com.difa.difaapp.ui.profile.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityKebijakanBinding
import com.difa.difaapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackSetting.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.iv_back_setting -> {
                finish()
            }
        }
    }
}