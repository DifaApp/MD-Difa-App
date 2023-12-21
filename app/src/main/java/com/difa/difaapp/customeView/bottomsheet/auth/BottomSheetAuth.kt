package com.difa.difaapp.customeView.bottomsheet.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.difa.difaapp.R
import com.difa.difaapp.ui.MainActivity
import com.difa.difaapp.ui.auth.login.LoginActivity
import com.difa.difaapp.utils.BottomSheetAuthType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAuth(private val type: BottomSheetAuthType): BottomSheetDialogFragment() {

    private lateinit var animationAuth: LottieAnimationView
    private lateinit var cardAuth: CardView
    private lateinit var tvTitleBsAuth: TextView
    private lateinit var tvSubTitleBsAuth: TextView
    private lateinit var tvBtnBsAuth: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bs_auth, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationAuth = view.findViewById(R.id.lottieAnimationAuth)
        cardAuth = view.findViewById(R.id.card_auth)

        tvTitleBsAuth = view.findViewById(R.id.tv_title_bs_auth)
        tvSubTitleBsAuth = view.findViewById(R.id.tv_sub_title_bs_auth)
        tvBtnBsAuth = view.findViewById(R.id.tv_bs_btn_auth)


        when(type){
            BottomSheetAuthType.LOGIN_TYPE -> {
                tvTitleBsAuth.text = requireActivity().getString(R.string.text_title_bs_auth_login)
                tvSubTitleBsAuth.text = requireActivity().getString(R.string.text_sub_title_bs_auth_login)
                tvBtnBsAuth.text = requireActivity().getString(R.string.text_lanjut)

                cardAuth.setOnClickListener {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
            BottomSheetAuthType.REGISTER_TYPE -> {
                tvTitleBsAuth.text = requireActivity().getString(R.string.text_title_bs_auth_register)
                tvSubTitleBsAuth.text = requireActivity().getString(R.string.text_sub_title_bs_auth_register)
                tvBtnBsAuth.text = requireActivity().getString(R.string.text_login)

                cardAuth.setOnClickListener {
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
                }
            }
            BottomSheetAuthType.UPDATE_TYPE -> {
                tvTitleBsAuth.text = requireActivity().getString(R.string.text_title_bs_auth_update)
                tvSubTitleBsAuth.text = requireActivity().getString(R.string.text_sub_title_bs_auth_update)
                tvBtnBsAuth.text = requireActivity().getString(R.string.text_kembali)

                cardAuth.setOnClickListener {
                    dismiss()
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                animationAuth.playAnimation()
            }, DELAY
        )

    }

    companion object{
        const val DELAY_MILS = 1500L
        const val DELAY = 500L
    }
}