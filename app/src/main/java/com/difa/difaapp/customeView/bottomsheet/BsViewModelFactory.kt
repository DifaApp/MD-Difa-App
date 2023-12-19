package com.difa.difaapp.customeView.bottomsheet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.difa.difaapp.customeView.bottomsheet.logout.ViewModelBottomSheetLogout
import com.difa.difaapp.data.repository.AppRepository
import com.difa.difaapp.di.Injection
import com.difa.difaapp.ui.splashscreen.SplashScreenViewModel

class BsViewModelFactory private constructor(
    private val appRepository: AppRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ViewModelBottomSheetLogout::class.java) -> {
                ViewModelBottomSheetLogout(appRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: BsViewModelFactory? = null

        fun getInstance(context: Context): BsViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: BsViewModelFactory(Injection.providerRepository(context))
            }.also { INSTANCE = it }
    }
}