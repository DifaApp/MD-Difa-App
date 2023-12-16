package com.difa.difaapp.data.sharedpref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "AuthPreferences")
class AuthPreferences private constructor(private val authDataStore: DataStore<Preferences>) {
    private val userToken = stringPreferencesKey(TOKEN_USER_KEY)
    private val userName = stringPreferencesKey(USER_NAME_KEY)
    private val userUid = stringPreferencesKey(UID_USER_KEY)
    private val userAvatar = stringPreferencesKey(AVATAR_USER_KEY)
    private val userEmail = stringPreferencesKey(EMAIL_USER_KEY)



    companion object{
        private const val TOKEN_USER_KEY = "tokenUserKey"
        private const val USER_NAME_KEY = "userNameKey"
        private const val UID_USER_KEY = "uidUserKey"
        private const val AVATAR_USER_KEY = "avatarUserKey"
        private const val EMAIL_USER_KEY = "emailUserKey"
        private const val IS_USER_GMAIL = "isUserGmailKey"

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