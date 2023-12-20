package com.difa.difaapp.ui.objdetection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.difa.difaapp.data.local.entity.Recognition

class ObjectDetectionViewModel: ViewModel() {
    private val _recognitionList = MutableLiveData<Recognition>()
    val recognitionList: LiveData<Recognition> = _recognitionList

    fun updateData(recognitions: Recognition){
        _recognitionList.postValue(recognitions)
    }
}