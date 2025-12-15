package com.earneasyads.app.utils

import android.content.Context

class SharedPrefManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)

    fun saveWalletBalance(balance: Double) {
        with(sharedPref.edit()) {
            putFloat("wallet_balance", balance.toFloat())
            apply()
        }
    }

    fun getWalletBalance(): Double {
        return sharedPref.getFloat("wallet_balance", 0f).toDouble()
    }

    fun saveAuthToken(token: String) {
        with(sharedPref.edit()) {
            putString("auth_token", token)
            apply()
        }
    }

    fun getAuthToken(): String {
        return sharedPref.getString("auth_token", "") ?: ""
    }

    fun clearAll() {
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }
}