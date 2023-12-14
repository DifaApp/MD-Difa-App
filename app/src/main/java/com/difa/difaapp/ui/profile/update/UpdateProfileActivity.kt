package com.difa.difaapp.ui.profile.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUpdateProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackProfileAccount.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.iv_back_profile_account -> {
                finish()
            }
        }
    }
}