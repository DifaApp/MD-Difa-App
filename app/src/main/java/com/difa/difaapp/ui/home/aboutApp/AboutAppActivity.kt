package com.difa.difaapp.ui.home.aboutApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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

        val selectedImage = ContextCompat.getDrawable(this, R.drawable.ic_arrow_up_24)
        val notSelectedImage = ContextCompat.getDrawable(this, R.drawable.ic_arrow_down_24)

        binding.cardView1.setOnClickListener {
            isExpanded1 = !isExpanded1
            binding.hiddenContent1.visibility = if(isExpanded1) View.VISIBLE else View.GONE
            binding.separatorLine.visibility = if(!isExpanded1) View.VISIBLE else View.GONE
            binding.expandBtn1.setImageDrawable(
                if(isExpanded1) selectedImage else notSelectedImage
            )
        }

        binding.cardView2.setOnClickListener {
            isExpanded2 = !isExpanded2
            binding.hiddenContent2.visibility = if(isExpanded2) View.VISIBLE else View.GONE
            binding.separatorLine2.visibility = if(!isExpanded2) View.VISIBLE else View.GONE
            binding.expandBtn2.setImageDrawable(
                if(isExpanded2) selectedImage else notSelectedImage
            )
        }

        binding.cardView3.setOnClickListener {
            isExpanded3 = !isExpanded3
            binding.hiddenContent3.visibility = if(isExpanded3) View.VISIBLE else View.GONE
            binding.separatorLine3.visibility = if(!isExpanded3) View.VISIBLE else View.GONE
            binding.expandBtn3.setImageDrawable(
                if(isExpanded2) selectedImage else notSelectedImage
            )
        }
    }
}