package com.earneasyads.app.utils

import android.content.Context
import android.util.Log
import com.earneasyads.app.models.AdItem

class AnalyticsManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AD_VIEWS = "ad_views_count"
        private const val KEY_AD_CLICKS = "ad_clicks_count"
    }

    fun logAdView(ad: AdItem) {
        val count = sharedPref.getInt(KEY_AD_VIEWS, 0) + 1
        sharedPref.edit().putInt(KEY_AD_VIEWS, count).apply()
        Log.d("Analytics", "Ad viewed: ${ad.title}, total views: $count")
    }

    fun logAdClick(ad: AdItem) {
        val count = sharedPref.getInt(KEY_AD_CLICKS, 0) + 1
        sharedPref.edit().putInt(KEY_AD_CLICKS, count).apply()
        Log.d("Analytics", "Ad clicked: ${ad.title}, total clicks: $count")
    }

    fun getTotalAdViews(): Int {
        return sharedPref.getInt(KEY_AD_VIEWS, 0)
    }

    fun getTotalAdClicks(): Int {
        return sharedPref.getInt(KEY_AD_CLICKS, 0)
    }
}