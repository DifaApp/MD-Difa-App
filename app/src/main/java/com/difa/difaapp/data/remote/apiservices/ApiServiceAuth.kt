package com.difa.difaapp.data.remote.apiservices

import com.difa.difaapp.data.remote.response.DetailProfileResponse
import com.difa.difaapp.data.remote.response.LoginResponse
import com.difa.difaapp.data.remote.response.RegisterResponse
import com.difa.difaapp.data.remote.response.UpdateProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceAuth {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @PUT("profile/update-profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("birthdate") birthdate: String
    ) : UpdateProfileResponse

    @GET("auth/user/{userId}")
    suspend fun detailProfile(
        @Path("userId") userId: Int
    ) : DetailProfileResponse

    @FormUrlEncoded
    @POST("profile/add-profile-picture")
    suspend fun updatePhotoProfile(
        @Header("Authorization") token: String,
        @Field("imageUrl") imageUrl: String,
    ) : UpdateProfileResponse

}