package com.difa.difaapp.customeView.bottomsheet.pickgender

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelBottomSheetGender: ViewModel() {
    val gender = MutableLiveData<String>()
    var birtDate = MutableLiveData<String>()
}