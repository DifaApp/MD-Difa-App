package com.difa.difaapp.customeView.bottomsheet.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.difa.difaapp.R
import com.difa.difaapp.customeView.bottomsheet.BsViewModelFactory
import com.difa.difaapp.ui.auth.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class BottomSheetLogout: BottomSheetDialogFragment(), View.OnClickListener {

    private val viewModel by viewModels<ViewModelBottomSheetLogout> {
        BsViewModelFactory.getInstance(requireActivity().applicationContext)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bs_logout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.card_logout).setOnClickListener(this)
        view.findViewById<CardView>(R.id.card_cancle).setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }

    private fun clearUserSession(){
        viewModel.isLoginGoogle().observe(requireActivity(), Observer {
            if(it){
                auth.signOut()
                lifecycleScope.launch {
                    viewModel.clearSession()
                }
            }else {
                lifecycleScope.launch {
                    viewModel.clearSession()
                }
            }
        })
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.card_logout -> {
                clearUserSession()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
            R.id.card_cancle -> dismiss()
        }
    }
}