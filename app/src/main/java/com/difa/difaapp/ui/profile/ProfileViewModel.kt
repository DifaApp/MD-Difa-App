package com.difa.difaapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.difa.difaapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AppRepository) : ViewModel(){
    fun isLoginGoogle() = repository.isUserGoogle().asLiveData()

    suspend fun clearSession(){
        viewModelScope.launch {
            repository.clearTokenSession()
        }
    }

    fun getSessionNormalUser() = repository.getNormalUser().asLiveData()


    fun getSessionGoogleUser() = repository.getGoogleUser().asLiveData()
}