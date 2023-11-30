package com.difa.difaapp.ui.onboarding.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.difa.difaapp.ui.onboarding.itemOnboarding.FragmentOnboardingPage1
import com.difa.difaapp.ui.onboarding.itemOnboarding.FragmentOnboardingPage2
import com.difa.difaapp.ui.onboarding.itemOnboarding.FragmentOnboardingPage3

class SectionOnboardingPageAdapter(activity: AppCompatActivity, val fragmentList: List<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}