package com.difa.difaapp.ui.profile.update

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.difa.difaapp.R
import com.difa.difaapp.customeView.bottomsheet.auth.BottomSheetAuth
import com.difa.difaapp.customeView.bottomsheet.pickdate.BottomSheetPickDate
import com.difa.difaapp.customeView.bottomsheet.pickdate.ViewModelBottomSheetDate
import com.difa.difaapp.customeView.bottomsheet.pickgender.BottomSheetPickGender
import com.difa.difaapp.customeView.bottomsheet.pickgender.ViewModelBottomSheetGender
import com.difa.difaapp.data.Result
import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.remote.response.DetailProfileResponse
import com.difa.difaapp.databinding.ActivityUpdateProfileBinding
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.utils.BottomSheetAuthType
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class UpdateProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUpdateProfileBinding
    private val viewModel by viewModels<UpdateProfileViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    private lateinit var bsViewModel: ViewModelBottomSheetGender
    private lateinit var bsVewModelDate: ViewModelBottomSheetDate

    private lateinit var bottomSheetPickGender: BottomSheetPickGender
    private lateinit var bottomSheetPickBirtDay: BottomSheetPickDate
    private lateinit var bottomSheetUpdateProfile: BottomSheetAuth

    private lateinit var loadingUpdateProfile: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheetPickGender = BottomSheetPickGender()
        bottomSheetPickBirtDay = BottomSheetPickDate()
        bottomSheetUpdateProfile = BottomSheetAuth(BottomSheetAuthType.UPDATE_TYPE)

        bsViewModel = ViewModelProvider(this).get(ViewModelBottomSheetGender::class.java)
        bsVewModelDate = ViewModelProvider(this).get(ViewModelBottomSheetDate::class.java)

        loadingUpdateProfile = Dialog(this)

        val user = intent.getParcelableExtra<User>(USER_KEY)
        if(user != null){
            setDataUser(user)
        }

        bsViewModel.gender.observe(this, Observer { result->
            binding.tvJenisKel.text = result
        })

        bsVewModelDate.selectedDate.observe(this, Observer {result->
            binding.tvTglLahir.text = result
        })

        binding.ivBackProfileAccount.setOnClickListener(this)
        binding.llJenisKel.setOnClickListener(this)
        binding.llTglLahir.setOnClickListener(this)

    }

    private fun setDataUser(user: User) {
        binding.namaEditText.setText(user.name)
        binding.emailEditText.setText(user.email)

        binding.tvJenisKel.text = if(user.gender.isEmpty()) "-" else user.gender
        binding.tvTglLahir.text = if(user.birtDate.isEmpty()) "-" else user.birtDate

        binding.btnUpdateProfile.setOnClickListener{
            val checkEmail = binding.emailEditText.text.toString()
            val checkName = binding.namaEditText.text.toString()
            val checkGender = binding.tvJenisKel.text.toString()
            val checkDate = binding.tvTglLahir.text.toString()
            if (checkEmail.isEmpty() || checkName.isEmpty() || checkGender.equals("-") || checkDate.equals("-")){
                Snackbar.make(binding.root, "Tolong Isikan Inputan Dengan Benar", Snackbar.LENGTH_LONG).show()
            }else {
                viewModel.updateProfile(
                    checkName, checkEmail, checkGender, checkDate, user.token
                ).observe(this){result->
                    if(result != null){
                        when(result){
                            is Result.Loading -> {
                                showDialog()
                            }
                            is Result.Success -> {
                                dismissLoading()
                                val dataUser = User(
                                    user.id,
                                    checkName,
                                    checkEmail,
                                    checkDate,
                                    checkGender,
                                    "",
                                    user.token
                                )
                                updateDataUser(dataUser)
                                bottomSheetUpdateProfile.show(supportFragmentManager, "bottomSheetUpdateProfile")
                            }
                            is Result.Error -> {
                                dismissLoading()
                                Snackbar.make(binding.root, "Terjadi Error", Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dismissLoading() {
        if(loadingUpdateProfile.isShowing){
            loadingUpdateProfile.dismiss()
        }
    }

    private fun showDialog() {
        loadingUpdateProfile.setContentView(R.layout.bg_loading_auth)
        loadingUpdateProfile.setCancelable(false)
        loadingUpdateProfile.setCanceledOnTouchOutside(false)
        loadingUpdateProfile.show()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.iv_back_profile_account -> {
                finish()
            }
            R.id.ll_jenis_kel -> {
                bottomSheetPickGender.show(supportFragmentManager, "BottomSheetPickGender")
            }
            R.id.ll_tgl_lahir -> {
                bottomSheetPickBirtDay.show(supportFragmentManager, "BottomSheetPickDate")
            }
        }
    }

    private fun updateDataUser(dataUser: User) {
        viewModel.setUserNormal(dataUser)
    }

    companion object{
        const val TAG = "UpdateProfileActivity"
        const val USER_KEY = "user_key"
    }
}