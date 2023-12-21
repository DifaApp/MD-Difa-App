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

    private lateinit var loadingUpdateProfile: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheetPickGender = BottomSheetPickGender()
        bottomSheetPickBirtDay = BottomSheetPickDate()

        bsViewModel = ViewModelProvider(this).get(ViewModelBottomSheetGender::class.java)
        bsVewModelDate = ViewModelProvider(this).get(ViewModelBottomSheetDate::class.java)

        loadingUpdateProfile = Dialog(this)

        binding.ivBackProfileAccount.setOnClickListener(this)
        binding.llJenisKel.setOnClickListener(this)
        binding.llTglLahir.setOnClickListener(this)
        binding.btnUpdateProfile.setOnClickListener(this)

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        viewModel.getSessionNormalUser().observe(this){ user->
            binding.emailEditText.setText(user.email)
            binding.namaEditText.setText(user.name)

            if (!user.gender.isEmpty()){
                binding.tvJenisKel.text = user.gender
            }else {
                binding.tvJenisKel.text = "-"
            }

            val date = format.parse(user.birtDate)
            val birthDateString = format.format(date)

            if (!user.birtDate.isEmpty()){
                binding.tvTglLahir.text = birthDateString
            }else {
                binding.tvTglLahir.text = "-"
            }


        }

        bsViewModel.gender.observe(this, Observer { result->
            binding.tvJenisKel.text = result
        })

        bsVewModelDate.selectedDate.observe(this, Observer {result->
            binding.tvTglLahir.text = result
        })
    }

    private fun dismissLoading() {
        if(loadingUpdateProfile.isShowing){
            loadingUpdateProfile.dismiss()
        }
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.text_title_bs_auth_update))
            setMessage(getString(R.string.text_sub_title_bs_auth_update))
            setPositiveButton(getString(R.string.text_kembali)) { _, _ ->
                finish()
            }
            create()
            show()
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
            R.id.btn_update_profile -> {
                val checkEmail = binding.emailEditText.text.toString()
                val checkName = binding.namaEditText.text.toString()
                val checkGender = binding.tvJenisKel.text.toString()
                val checkDate = binding.tvTglLahir.text.toString()
                if (checkEmail.isEmpty() || checkName.isEmpty() || checkGender.equals("-") || checkDate.equals("-")){
                    Snackbar.make(binding.root, "Tolong Isikan Inputan Dengan Benar", Snackbar.LENGTH_LONG).show()
                }else {
                    viewModel.getSessionNormalUser().observe(this){user->
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
                                        alertDialog()
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
        }
    }

    companion object{
        const val TAG = "UpdateProfileActivity"
    }
}