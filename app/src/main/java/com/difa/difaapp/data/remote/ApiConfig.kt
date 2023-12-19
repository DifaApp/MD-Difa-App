package com.difa.difaapp.data.remote

import com.difa.difaapp.BuildConfig
import com.difa.difaapp.data.remote.apiservices.ApiServiceAuth
import com.difa.difaapp.data.remote.apiservices.ApiServiceRecommendation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private const val BASE_URL_AUTH = BuildConfig.BASE_URL_AUTH
        private const val BASE_URL_RECOM = BuildConfig.BASE_URL_RECOM
        val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY) } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)}
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofitAuth = Retrofit.Builder()
            .baseUrl(BASE_URL_AUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val retrofitRecomendation = Retrofit.Builder()
            .baseUrl(BASE_URL_RECOM)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val apiServiceAuth = retrofitAuth.create(ApiServiceAuth::class.java)
        val apiServiceRecomendation = retrofitRecomendation.create(ApiServiceRecommendation::class.java)
    }
}