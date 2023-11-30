package com.difa.difaapp.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.difa.difaapp.R
import com.difa.difaapp.databinding.FragmentOnboardingBinding
import com.difa.difaapp.ui.onboarding.adapter.SectionOnboardingPageAdapter
import com.difa.difaapp.ui.onboarding.itemOnboarding.FragmentOnboardingPage1
import com.difa.difaapp.ui.onboarding.itemOnboarding.FragmentOnboardingPage2
import com.difa.difaapp.ui.onboarding.itemOnboarding.FragmentOnboardingPage3


class OnboardingFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val listFragment: List<Fragment> = listOf(
            FragmentOnboardingPage1(),
            FragmentOnboardingPage2(),
            FragmentOnboardingPage3(),
        )

        val sectionOnboardingPageAdapter = SectionOnboardingPageAdapter(requireActivity() as AppCompatActivity, listFragment)
        binding.viewPager.adapter = sectionOnboardingPageAdapter
        binding.viewPager.registerOnPageChangeCallback(object: OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        binding.apply {
                            consNormalDot1.visibility = View.GONE
                            consSelectedDot1.visibility = View.VISIBLE

                            consNormalDot2.visibility = View.VISIBLE
                            consSelectedDot2.visibility = View.GONE

                            consNormalDot3.visibility = View.VISIBLE
                            consSelectedDot3.visibility = View.GONE
                        }
                    }
                    1 -> {
                        binding.apply {
                            consNormalDot1.visibility = View.VISIBLE
                            consSelectedDot1.visibility = View.GONE

                            consNormalDot2.visibility = View.GONE
                            consSelectedDot2.visibility = View.VISIBLE

                            consNormalDot3.visibility = View.VISIBLE
                            consSelectedDot3.visibility = View.GONE
                        }
                    }
                    2 -> {
                        binding.apply {
                            consNormalDot1.visibility = View.VISIBLE
                            consSelectedDot1.visibility = View.GONE

                            consNormalDot2.visibility = View.VISIBLE
                            consSelectedDot2.visibility = View.GONE

                            consNormalDot3.visibility = View.GONE
                            consSelectedDot3.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        binding.btnNext.setOnClickListener(this)
        binding.tvSkip.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_next -> {
                val currentItem = binding.viewPager.currentItem
                when(currentItem){
                    0 -> binding.viewPager.setCurrentItem(1, true)
                    1 -> binding.viewPager.setCurrentItem(2, true)
                    2 -> {
                        binding.viewPager.setCurrentItem(3, true)
                        startActivity(Intent(requireActivity(), GetStartedActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
            R.id.tv_skip -> {
                startActivity(Intent(requireActivity(), GetStartedActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}