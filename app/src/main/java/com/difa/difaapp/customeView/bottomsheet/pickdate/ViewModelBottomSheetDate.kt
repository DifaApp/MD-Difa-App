package com.difa.difaapp.customeView.bottomsheet.pickdate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class ViewModelBottomSheetDate: ViewModel() {
    val selectedDate = MutableLiveData<String>()
}