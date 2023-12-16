package com.difa.difaapp.data.repository

import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.local.entity.UserGoogle
import com.difa.difaapp.data.remote.ApiService
import com.difa.difaapp.data.sharedpref.AppPreferences
import com.difa.difaapp.data.sharedpref.AuthPreferences
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val apiService: ApiService,
    private val prefApp: AppPreferences,
    private val prefAuth: AuthPreferences,
) {

    suspend fun clearTokenSession() {
        prefAuth.clearTokenSession()
    }

    suspend fun setSessionNormalLogin(user: User){
        prefAuth.setSessionUserNormalLogin(user)
    }

    suspend fun setSessionGoogleLogin(user: UserGoogle){
        prefAuth.setSessionUserGoogleLogin(user)
    }

    fun getGoogleUser(): Flow<UserGoogle> = prefAuth.getSessionUserGoogleLogin()

    fun getNormalUser(): Flow<User> = prefAuth.getSessionUserNormalLogin()

    suspend fun saveOnBoarding(isOnboarding: Boolean){
        prefApp.saveIsOnboardingUser(isOnboarding)
    }

    fun onBoarding(): Flow<Boolean> = prefApp.getIsOnBoardingUser()


    suspend fun saveIsUserGoogle(isUserWithGoogle: Boolean){
        prefAuth.saveIsSignInWithGoogle(isUserWithGoogle)
    }

    fun isUserGoogle(): Flow<Boolean> = prefAuth.getIsSignInWithGoogle()


    companion object{
        private var INSTANCE: AppRepository? = null
        fun getInstance(
            apiService: ApiService,
            prefApp: AppPreferences,
            prefAuth: AuthPreferences
        ) : AppRepository = INSTANCE ?: synchronized(this){
            INSTANCE ?: AppRepository(
                apiService,
                prefApp,
                prefAuth
            )
        }.also { INSTANCE = it }
    }
}