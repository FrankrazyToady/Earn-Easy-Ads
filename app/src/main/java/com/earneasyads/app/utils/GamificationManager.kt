package com.earneasyads.app.utils

import android.content.Context
import java.time.LocalDate

class GamificationManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_LOGIN_DATE = "last_login_date"
        private const val KEY_STREAK = "streak_count"
        private const val KEY_DAILY_GOAL_COMPLETED = "daily_goal_completed"
    }

    fun updateLogin() {
        val today = LocalDate.now().toString()
        val lastLogin = sharedPref.getString(KEY_LAST_LOGIN_DATE, "")
        val streak = sharedPref.getInt(KEY_STREAK, 0)

        if (lastLogin != today) {
            // If yesterday was last login, increment streak
            val yesterday = LocalDate.now().minusDays(1).toString()
            val newStreak = if (lastLogin == yesterday) streak + 1 else 1

            with(sharedPref.edit()) {
                putString(KEY_LAST_LOGIN_DATE, today)
                putInt(KEY_STREAK, newStreak)
                putBoolean(KEY_DAILY_GOAL_COMPLETED, false)
                apply()
            }
        }
    }

    fun completeDailyGoal() {
        with(sharedPref.edit()) {
            putBoolean(KEY_DAILY_GOAL_COMPLETED, true)
            apply()
        }
    }

    fun isDailyGoalCompleted(): Boolean {
        return sharedPref.getBoolean(KEY_DAILY_GOAL_COMPLETED, false)
    }

    fun getStreakCount(): Int {
        return sharedPref.getInt(KEY_STREAK, 0)
    }
}