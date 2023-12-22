package com.difa.difaapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.local.entity.UserGoogle
import com.difa.difaapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AppRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    fun isUserLoginWithGoogle(isLoginWithGoogle: Boolean){
        viewModelScope.launch {
            repository.saveIsUserGoogle(isLoginWithGoogle)
        }
    }

    fun profileUser(id: Int) = repository.profileUser(id)

    fun setUserNormal(user: User){
        viewModelScope.launch {
            repository.setSessionNormalLogin(user)
        }
    }

    fun setUserGoogle(user: UserGoogle){
        viewModelScope.launch {
            repository.setSessionGoogleLogin(user)
        }
    }
}