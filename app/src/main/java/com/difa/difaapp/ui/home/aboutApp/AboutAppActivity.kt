package com.difa.difaapp.ui.home.aboutApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.difa.difaapp.R
import com.difa.difaapp.databinding.ActivityAboutAppBinding

class AboutAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutAppBinding

    private var isExpanded1 = false
    private var isExpanded2 = false
    private var isExpanded3 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackAboutApp.setOnClickListener {
            finish()
        }

        binding.cardView1.setOnClickListener {
            isExpanded1 = !isExpanded1
            binding.hiddenContent1.visibility = if(isExpanded1) View.VISIBLE else View.GONE
            binding.separatorLine.visibility = if(!isExpanded1) View.VISIBLE else View.GONE
        }

        binding.cardView2.setOnClickListener {
            isExpanded2 = !isExpanded2
            binding.hiddenContent2.visibility = if(isExpanded2) View.VISIBLE else View.GONE
            binding.separatorLine2.visibility = if(!isExpanded2) View.VISIBLE else View.GONE
        }

        binding.cardView3.setOnClickListener {
            isExpanded3 = !isExpanded3
            binding.hiddenContent3.visibility = if(isExpanded3) View.VISIBLE else View.GONE
            binding.separatorLine3.visibility = if(!isExpanded3) View.VISIBLE else View.GONE
        }
    }
}