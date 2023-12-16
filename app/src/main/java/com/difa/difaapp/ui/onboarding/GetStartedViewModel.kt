package com.difa.difaapp.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.difa.difaapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class GetStartedViewModel(private val repository: AppRepository): ViewModel() {

    fun saveOnboarding(isOnboarding: Boolean){
        viewModelScope.launch {
            repository.saveOnBoarding(isOnboarding)
        }
    }

}