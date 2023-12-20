package com.difa.difaapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.local.entity.UserGoogle
import com.difa.difaapp.data.local.room.DifaAppDatabase
import com.difa.difaapp.data.remote.apiservices.ApiServiceAuth
import com.difa.difaapp.data.remote.apiservices.ApiServiceRecommendation
import com.difa.difaapp.data.remote.response.RegisterResponse
import com.difa.difaapp.data.sharedpref.AppPreferences
import com.difa.difaapp.data.sharedpref.AuthPreferences
import kotlinx.coroutines.flow.Flow
import com.difa.difaapp.data.Result
import com.difa.difaapp.data.local.entity.Recommendation
import com.difa.difaapp.data.remote.response.DetailProfileResponse
import com.difa.difaapp.data.remote.response.LoginResponse
import com.difa.difaapp.data.remote.response.UpdateProfileResponse

class AppRepository(
    private val database: DifaAppDatabase,
    private val apiServiceAuth: ApiServiceAuth,
    private val apiServiceRecom: ApiServiceRecommendation,
    private val prefApp: AppPreferences,
    private val prefAuth: AuthPreferences,
) {

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServiceAuth.login(email, password)
            val error = response.error
            val result = response.loginResult
            if(!error){
                prefAuth.clearTokenSession()
                val user = User(
                    id = result.userId,
                    name = result.name,
                    token = result.token,
                    email = "",
                    gender = "",
                    birtDate = "",
                    avatar = ""
                )
                prefAuth.setSessionUserNormalLogin(user)
                emit(Result.Success(response))
            }
        }catch (e: Exception){
            Log.d("AppRepository", "LoginUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun registerUser(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiServiceAuth.register(
                name,
                email,
                password
            )
            val error = response.error
            if (!error){
                emit(Result.Success(response))
            }else {
                emit(Result.Error(response.message))
            }
        }catch (e: Exception){
            Log.d("AppRepository", "registerUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateProfile(name: String, email: String, gender: String, birthDay: String, token: String): LiveData<Result<UpdateProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServiceAuth.updateProfile(
                token = setGenerateToken(token),
                name = name,
                email = email,
                gender = gender,
                birthdate = birthDay
            )
            val error = response.error
            if (!error){
                emit(Result.Success(response))
            }else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception){
            Log.d("AppRepository", "UpdateProfile: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun profileUser(userId: Int): LiveData<Result<DetailProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServiceAuth.detailProfile(userId)
            val error = response.error
            if (!error){
                emit(Result.Success(response))
            }else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception){
            Log.d("AppRepository", "profileUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllRecommendation(): LiveData<Result<List<Recommendation>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServiceRecom.getAllRecommendation()
            val error = response.error
            if(!error){
                val listRecom = response.recommendations
                val recommendation = listRecom.map { recom ->
                    Recommendation(
                        id = recom.id,
                        title = recom.title,
                        description = recom.description,
                        urlImage = recom.urlImage,
                        urlVideo = recom.urlVideo
                    )
                }
                database.recommendationDao().deleteStory()
                database.recommendationDao().insertRecommendation(recommendation)
            }else {
                emit(Result.Error(response.message))
            }
        }catch (e: Exception){
            Log.d("AppRepository", "getAllRecommendation: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<Recommendation>>> = database.recommendationDao().getAllRecommendation().map { Result.Success(it) }
        emitSource(localData)
    }

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

    fun setGenerateToken(token: String): String = "Bearer $token"


    companion object{
        private var INSTANCE: AppRepository? = null
        fun getInstance(
            difaAppDatabase: DifaAppDatabase,
            apiServiceAuth: ApiServiceAuth,
            apiServiceRecom: ApiServiceRecommendation,
            prefApp: AppPreferences,
            prefAuth: AuthPreferences
        ) : AppRepository = INSTANCE ?: synchronized(this){
            INSTANCE ?: AppRepository(
                difaAppDatabase,
                apiServiceAuth,
                apiServiceRecom,
                prefApp,
                prefAuth
            )
        }.also { INSTANCE = it }
    }
}