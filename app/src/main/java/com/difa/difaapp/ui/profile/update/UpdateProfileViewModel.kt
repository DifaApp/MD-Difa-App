package com.difa.difaapp.ui.profile.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val repository: AppRepository): ViewModel(){

    fun updateProfile(name: String, email:String, gender: String, birtDay: String, token: String) = repository.updateProfile(
        name, email, gender, birtDay, token
    )

    fun setUserNormal(user: User){
        viewModelScope.launch {
            repository.setSessionNormalLogin(user)
        }
    }
}