package com.difa.difaapp.data.remote.apiservices

import com.difa.difaapp.data.remote.response.RecommendationResponse
import retrofit2.http.GET

interface ApiServiceRecommendation {

    @GET("recommendations")
    suspend fun getAllRecommendation() : RecommendationResponse
}