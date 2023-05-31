package com.ennovations.fitcrucoach.Network

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.TOKEN, null)
    }
}