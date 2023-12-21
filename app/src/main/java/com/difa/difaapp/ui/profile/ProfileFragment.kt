package com.difa.difaapp.ui.profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.difa.difaapp.R
import com.difa.difaapp.customeView.bottomsheet.logout.BottomSheetLogout
import com.difa.difaapp.data.Result
import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.remote.response.DetailProfileResponse
import com.difa.difaapp.databinding.FragmentProfileBinding
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.ui.profile.kebijakan.KebijakanActivity
import com.difa.difaapp.ui.profile.setting.SettingActivity
import com.difa.difaapp.ui.profile.update.UpdateProfileActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }
    private lateinit var bottomSheetLogout: BottomSheetLogout
    private lateinit var fragmentManager: FragmentManager
    private lateinit var loadingProfile: Dialog

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

        bottomSheetLogout = BottomSheetLogout()
        fragmentManager = childFragmentManager
        loadingProfile = Dialog(requireActivity())

        viewModel.isLoginGoogle().observe(requireActivity(), Observer {
            binding.linearRootProfile.visibility = if(it) View.GONE else View.VISIBLE
            binding.tvLabelSecProfile.visibility = if(it) View.GONE else View.VISIBLE
            if(it){
                viewModel.getSessionGoogleUser().observe(requireActivity()){
                    Glide.with(requireActivity())
                        .load(it.avatar)
                        .into(binding.ivImageProfile)

                    binding.tvProfileName.text = it.name
                    binding.tvEmail.text = it.email
                }
            }else {
                viewModel.getSessionNormalUser().observe(requireActivity()){user->
                    setupProfile(user)
                }
            }
        })

        binding.linearRootPrivacy.setOnClickListener(this)
        binding.linearRootProfile.setOnClickListener(this)
        binding.linearRootSettings.setOnClickListener(this)
        binding.linearRootLogout.setOnClickListener(this)
    }

    private fun setupProfile(data: User) {
        binding.tvProfileName.text = data.name
        binding.tvEmail.text = data.email
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
                bottomSheetLogout.show(fragmentManager, "BottomCreatePost")
            }
        }
    }

    companion object{
        private const val TAG = "ProfileFragment"
        const val DELAY_MILIS = 1500L
    }
}