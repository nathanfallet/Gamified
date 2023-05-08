package me.nathanfallet.gamified.services

import android.content.SharedPreferences

data class SharedPreferencesGlobalStorageService(
    val sharedPreferences: SharedPreferences
) : GlobalStorageService {

    override fun getValue(key: String): Long {
        return sharedPreferences.getLong("gamified_$key", 0)
    }

    override fun setValue(key: String, value: Long) {
        sharedPreferences.edit()
            .putLong("gamified_$key", value)
            .apply()
    }

}