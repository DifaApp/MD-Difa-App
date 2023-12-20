package com.difa.difaapp.ui.auth.register

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.difa.difaapp.R
import com.difa.difaapp.customeView.bottomsheet.auth.BottomSheetAuth
import com.difa.difaapp.data.local.entity.UserGoogle
import com.difa.difaapp.databinding.ActivityRegisterBinding
import com.difa.difaapp.ui.MainActivity
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.utils.BottomSheetAuthType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.difa.difaapp.data.Result
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    private lateinit var loadingAuth: Dialog
    private lateinit var bottomSheetRegister: BottomSheetAuth

    private var resultSignUpLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle: $account.id")
                firebaseAuthWithGoogle(account.idToken)
            }catch (e: ApiException){
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth
        bottomSheetRegister = BottomSheetAuth(BottomSheetAuthType.REGISTER_TYPE)

        binding.ivBack.setOnClickListener(this)
        binding.btnRegisterGoogle.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)

        loadingAuth = Dialog(this)

    }


    private fun userRegister(name: String, email: String, password: String) {
        viewModel.register(name,email,password).observe(this) {result->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        showDialog()
                    }
                    is Result.Success -> {
                        dismissLoading()
                        bottomSheetRegister.show(supportFragmentManager, "BottomSheetRegister")
                    }
                    is Result.Error -> {
                        dismissLoading()
                        Snackbar.make(binding.root, "Terjadi Error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun signUpWithGoogle() {
        val signUpIntent = googleSignInClient.signInIntent
        resultSignUpLauncher.launch(signUpIntent)
    }

    private fun dismissLoading() {
        if(loadingAuth.isShowing){
            loadingAuth.dismiss()
        }
    }

    private fun showDialog() {
        loadingAuth.setContentView(R.layout.bg_loading_auth)
        loadingAuth.setCancelable(false)
        loadingAuth.setCanceledOnTouchOutside(false)
        loadingAuth.show()
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            viewModel.isUserLoginWithGoogle(true)
            val user = UserGoogle(
                id = user.uid,
                name = user.displayName ?: "",
                email = user.email ?: "",
                avatar = user.photoUrl.toString()
            )
            viewModel.setUserGoogle(user)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.iv_back -> finish()
            R.id.btn_register_google -> signUpWithGoogle()
            R.id.btn_register -> {
                if(binding.nameEditText.toString().isEmpty() || binding.emailEditText.toString().isEmpty() || binding.passwordEditText.toString().isEmpty()){
                    Snackbar.make(binding.root, "Tolong isi email anda terlebih dahulu", Snackbar.LENGTH_LONG).show()
                }else {
                    val email = binding.emailEditText.text.toString()
                    val password = binding.passwordEditText.text.toString()
                    val name = binding.nameEditText.text.toString()
                    userRegister(name, email, password)
                }
                Log.d(TAG, "onClick: ")
            }
        }
    }

    companion object{
        private const val TAG = "RegisterActivity"
        const val DELAY_MILIS = 1500L
    }
}