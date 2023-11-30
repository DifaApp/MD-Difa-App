package com.difa.difaapp.ui.onboarding.itemOnboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.difa.difaapp.R
import com.difa.difaapp.databinding.FragmentOnboardingPage3Binding
import com.difa.difaapp.ui.auth.login.LoginActivity
import com.difa.difaapp.ui.onboarding.GetStartedActivity


class FragmentOnboardingPage3 : Fragment() {

    private lateinit var binding: FragmentOnboardingPage3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingPage3Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}