package com.difa.difaapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.difa.difaapp.R
import com.difa.difaapp.databinding.FragmentProfileBinding
import com.difa.difaapp.ui.profile.kebijakan.KebijakanActivity
import com.difa.difaapp.ui.profile.setting.SettingActivity
import com.difa.difaapp.ui.profile.update.UpdateProfileActivity

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearRootPrivacy.setOnClickListener(this)
        binding.linearRootProfile.setOnClickListener(this)
        binding.linearRootSettings.setOnClickListener(this)
        binding.linearRootLogout.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.linear_root_privacy -> {
                startActivity(Intent(requireActivity(), KebijakanActivity::class.java))
            }
            R.id.linear_root_profile -> {
                startActivity(Intent(requireActivity(), UpdateProfileActivity::class.java))
            }
            R.id.linear_root_settings -> {
                startActivity(Intent(requireActivity(), SettingActivity::class.java))
            }
            R.id.linear_root_logout -> {
                requireActivity().finish()
            }
        }
    }
}