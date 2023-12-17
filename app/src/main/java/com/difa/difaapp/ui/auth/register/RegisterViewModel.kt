package com.difa.difaapp.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.difa.difaapp.data.local.entity.UserGoogle
import com.difa.difaapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AppRepository) : ViewModel() {

    fun setUserGoogle(user: UserGoogle) {
        viewModelScope.launch {
            repository.setSessionGoogleLogin(user)
        }
    }

    fun isUserLoginWithGoogle(isLoginWithGoogle: Boolean) {
        viewModelScope.launch {
            repository.saveIsUserGoogle(true)
        }
    }
}

