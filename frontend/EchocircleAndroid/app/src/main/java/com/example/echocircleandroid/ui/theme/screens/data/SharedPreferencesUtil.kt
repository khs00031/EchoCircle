package com.example.echocircleandroid.ui.theme.screens.data

import android.content.Context
import android.content.SharedPreferences

// 페이지가 바뀌거나 앱이 종료되도 email, token값을 소유하는Util
object SharedPreferencesUtil {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_USER_EMAIL = "user_email"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, token: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_AUTH_TOKEN, null)
    }

    fun saveUserEmail(context: Context, email: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun clearAll(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().clear().apply()
    }
}
