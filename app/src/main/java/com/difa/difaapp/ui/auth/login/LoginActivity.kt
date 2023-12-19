package com.difa.difaapp.ui.auth.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.difa.difaapp.R
import com.difa.difaapp.customeView.bottomsheet.auth.BottomSheetAuth
import com.difa.difaapp.data.local.entity.UserGoogle
import com.difa.difaapp.databinding.ActivityLoginBinding
import com.difa.difaapp.ui.MainActivity
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.ui.auth.register.RegisterActivity
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

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loadingAuth: Dialog

    private lateinit var bottomSheetLogin: BottomSheetAuth

    private var resultSignInLauncher = registerForActivityResult(
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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth
        bottomSheetLogin = BottomSheetAuth(BottomSheetAuthType.LOGIN_TYPE)

        binding.tvSignUp.setOnClickListener(this)
        binding.btnLoginWithGoogle.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

        loadingAuth = Dialog(this)
    }

    private fun singInWithGoogle() {
        val singInIntent = googleSignInClient.signInIntent
        resultSignInLauncher.launch(singInIntent)
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

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null){
            viewModel.isUserLoginWithGoogle(true)
            val user = UserGoogle(
                id = firebaseUser.uid,
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                avatar = firebaseUser.photoUrl.toString()
            )
            viewModel.setUserGoogle(user)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {

        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.tv_sign_up -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.btn_login_with_google -> singInWithGoogle()
            R.id.btn_login -> bottomSheetLogin.show(supportFragmentManager, "BottomSheetLogin")
        }
    }

    companion object{
        private val TAG = "LoginActivity"
    }
}