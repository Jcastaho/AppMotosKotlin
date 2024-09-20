package com.straccion.appmotos1

import android.content.Context


class SharedPreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUidUser(uidUser: String) {
        sharedPreferences.edit().putString("uidUser", uidUser).apply()
    }

    fun getUidUser(): String? {
        return sharedPreferences.getString("uidUser", null)
    }

    fun hasUidUser(): Boolean {
        return sharedPreferences.contains("uidUser")
    }
}