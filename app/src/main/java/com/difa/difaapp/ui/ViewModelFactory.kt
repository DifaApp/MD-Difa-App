package com.difa.difaapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.difa.difaapp.data.repository.AppRepository
import com.difa.difaapp.di.Injection
import com.difa.difaapp.ui.auth.login.LoginViewModel
import com.difa.difaapp.ui.auth.register.RegisterViewModel
import com.difa.difaapp.ui.home.HomeViewModel
import com.difa.difaapp.ui.onboarding.GetStartedViewModel
import com.difa.difaapp.ui.profile.ProfileViewModel
import com.difa.difaapp.ui.profile.setting.SettingViewModel
import com.difa.difaapp.ui.profile.update.UpdateProfileViewModel
import com.difa.difaapp.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory private constructor(
    private val appRepository: AppRepository
): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(GetStartedViewModel::class.java) -> {
                GetStartedViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java)-> {
                LoginViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(UpdateProfileViewModel::class.java) -> {
                UpdateProfileViewModel(appRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context):ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(Injection.providerRepository(context))
            }.also { INSTANCE = it }
    }
}