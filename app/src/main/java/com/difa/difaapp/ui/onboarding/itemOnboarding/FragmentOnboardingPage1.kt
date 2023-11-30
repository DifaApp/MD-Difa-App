package com.difa.difaapp.ui.onboarding.itemOnboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.difa.difaapp.R
import com.difa.difaapp.databinding.FragmentOnboardingPage1Binding

class FragmentOnboardingPage1 : Fragment() {

    private lateinit var binding: FragmentOnboardingPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingPage1Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}