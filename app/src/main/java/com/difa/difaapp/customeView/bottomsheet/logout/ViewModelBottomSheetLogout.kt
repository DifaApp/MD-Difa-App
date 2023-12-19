package com.difa.difaapp.customeView.bottomsheet.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.difa.difaapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class ViewModelBottomSheetLogout(private val repository: AppRepository) : ViewModel() {
    fun isLoginGoogle() = repository.isUserGoogle().asLiveData()

    suspend fun clearSession(){
        viewModelScope.launch {
            repository.clearTokenSession()
        }
    }
}