package com.difa.difaapp.di

import android.content.Context
import com.difa.difaapp.data.remote.ApiConfig
import com.difa.difaapp.data.repository.AppRepository
import com.difa.difaapp.data.sharedpref.AppPreferences
import com.difa.difaapp.data.sharedpref.AuthPreferences
import com.difa.difaapp.data.sharedpref.appDataStore
import com.difa.difaapp.data.sharedpref.authDataStore

object Injection {
    fun providerRepository(context: Context): AppRepository {
        val prefApp = AppPreferences.getInstance(context.appDataStore)
        val prefAuth = AuthPreferences.getInstance(context.authDataStore)
        val apiServiceAuth = ApiConfig.apiServiceAuth
        val apiServiceRecom = ApiConfig.apiServiceRecomendation
        return AppRepository(
            apiServiceAuth = apiServiceAuth,
            apiServiceRecom = apiServiceRecom,
            prefApp = prefApp,
            prefAuth = prefAuth
        )
    }
}