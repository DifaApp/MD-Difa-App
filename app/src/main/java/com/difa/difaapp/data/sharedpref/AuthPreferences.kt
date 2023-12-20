package com.difa.difaapp.data.sharedpref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.difa.difaapp.data.local.entity.User
import com.difa.difaapp.data.local.entity.UserGoogle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "AuthPreferences")
class AuthPreferences private constructor(private val authDataStore: DataStore<Preferences>) {
    private val userToken = stringPreferencesKey(TOKEN_USER_KEY)
    private val userName = stringPreferencesKey(USER_NAME_KEY)
    private val userIdNormal = intPreferencesKey(UID_USER_KEY)
    private val userIdGoogle = stringPreferencesKey(UID_USER_KEY)
    private val userAvatar = stringPreferencesKey(AVATAR_USER_KEY)
    private val userEmail = stringPreferencesKey(EMAIL_USER_KEY)

    private val userBirthDay = stringPreferencesKey(BIRTH_DATE_KEY)
    private val userGender = stringPreferencesKey(GENDER_USER_KEY)

    private val isSignInWithGoogle = booleanPreferencesKey(IS_SIGNINWITHGOOGLE_KEY)

    fun getSessionUserNormalLogin(): Flow<User> {
        return authDataStore.data.map {pref->
            User(
                id = pref[userIdNormal] ?: 0,
                name = pref[userName] ?: "",
                email = pref[userEmail] ?: "",
                birtDate = pref[userBirthDay] ?: "",
                gender = pref[userGender] ?: "",
                avatar = pref[userAvatar] ?: "",
                token = pref[userToken] ?: ""
            )
        }
    }

    suspend fun setSessionUserNormalLogin(user: User){
        authDataStore.edit {pref->
            pref[userIdNormal] = user.id
            pref[userName] = user.name
            pref[userEmail] = user.email
            pref[userBirthDay] = user.birtDate
            pref[userGender] = user.gender
            pref[userAvatar] = user.avatar ?: ""
            pref[userToken] = user.token ?: ""
        }
    }

    fun getSessionUserGoogleLogin(): Flow<UserGoogle> {
        return authDataStore.data.map {pref->
            UserGoogle(
                id = pref[userIdGoogle] ?: "",
                name = pref[userName] ?: "",
                email = pref[userEmail] ?: "",
                avatar = pref[userAvatar] ?: ""
            )
        }
    }

    suspend fun setSessionUserGoogleLogin(user: UserGoogle){
        authDataStore.edit {pref->
            pref[userIdGoogle] = user.id
            pref[userName] = user.name
            pref[userEmail] = user.email
            pref[userAvatar] = user.avatar ?: ""
        }
    }

    suspend fun clearTokenSession(){
        authDataStore.edit {preferences ->
            preferences.clear()
        }
    }

    suspend fun saveIsSignInWithGoogle(isSignInWithGoogle: Boolean){
        authDataStore.edit { preferences ->
            preferences[this.isSignInWithGoogle] = isSignInWithGoogle
        }
    }

    fun getIsSignInWithGoogle(): Flow<Boolean> {
        return authDataStore.data.map { preferences ->
            preferences[isSignInWithGoogle] ?: false
        }
    }


    companion object{
        private const val TOKEN_USER_KEY = "tokenUserKey"
        private const val USER_NAME_KEY = "userNameKey"
        private const val UID_USER_KEY = "uidUserKey"
        private const val AVATAR_USER_KEY = "avatarUserKey"
        private const val EMAIL_USER_KEY = "emailUserKey"

        private const val BIRTH_DATE_KEY = "birtDateKey"
        private const val GENDER_USER_KEY = "genderUserKey"

        private const val IS_SIGNINWITHGOOGLE_KEY = "isSignInWithGoogleKey"


        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}