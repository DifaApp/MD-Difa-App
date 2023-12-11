package com.difa.difaapp.ui.objdetection.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.difa.difaapp.R
import com.difa.difaapp.databinding.FragmentCameraBinding


class CameraFragment : Fragment() {

    private var _fragmentCameraBinding: FragmentCameraBinding? = null
    private val fragmentCameraBinding
        get() = _fragmentCameraBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentCameraBinding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return fragmentCameraBinding.root
    }

    override fun onResume() {
        super.onResume()
        if(!PermissionFragment.hasPermissions(requireContext())){
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.action_cameraFragment_to_permissionFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentCameraBinding = null
    }
}