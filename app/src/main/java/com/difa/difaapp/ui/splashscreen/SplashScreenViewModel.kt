package com.difa.difaapp.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.difa.difaapp.data.repository.AppRepository

class SplashScreenViewModel(private val repository: AppRepository): ViewModel() {

    fun isOnboarding() = repository.onBoarding().asLiveData()

    fun isLoginGoogle() = repository.isUserGoogle().asLiveData()
    fun getSessionNormalUser() = repository.getNormalUser().asLiveData()


    fun getSessionGoogleUser() = repository.getGoogleUser().asLiveData()
}