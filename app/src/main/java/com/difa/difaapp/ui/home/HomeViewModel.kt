package com.difa.difaapp.ui.home

import androidx.lifecycle.ViewModel
import com.difa.difaapp.data.repository.AppRepository

class HomeViewModel(private val repository: AppRepository) : ViewModel(){
    fun getAllRecommendation() = repository.getAllRecommendation()
}