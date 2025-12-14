package com.earneasyads.app.activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class HomeActivity : AppCompatActivity() {

    private lateinit var tvWalletBalance: TextView
    private lateinit var btnWatchAd: Button
    private var walletBalance: Double = 0.0

    private var rewardedAd: RewardedAd? = null
    private val adUnitId = "ca-app-pub-3940256099942544/5224354917" // Test AdMob ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvWalletBalance = findViewById(R.id.tvWalletBalance)
        btnWatchAd = findViewById(R.id.btnWatchAd)

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this) {}

        // Load wallet balance from SharedPreferences
        walletBalance = getWalletBalance()
        updateWalletUI()

        // Load the rewarded ad
        loadRewardedAd()

        btnWatchAd.setOnClickListener {
            showRewardedAd()
        }
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
            }

            override fun onAdFailedToLoad(error: com.google.android.gms.ads.LoadAdError) {
                rewardedAd = null
                Toast.makeText(this@HomeActivity, "Ad failed to load: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRewardedAd() {
        rewardedAd?.let { ad ->
            ad.show(this) { rewardItem: RewardItem ->
                val rewardAmount = rewardItem.amount.toDouble()
                walletBalance += rewardAmount
                saveWalletBalance(walletBalance)
                updateWalletUI()
                Toast.makeText(this, "You earned $$rewardAmount!", Toast.LENGTH_SHORT).show()
                loadRewardedAd() // Load next ad
            }
        } ?: Toast.makeText(this, "Ad not ready yet", Toast.LENGTH_SHORT).show()
    }

    private fun updateWalletUI() {
        tvWalletBalance.text = "Wallet: $%.2f".format(walletBalance)
    }

    // SharedPreferences helper functions
    private fun saveWalletBalance(balance: Double) {
        val sharedPref = getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putFloat("wallet_balance", balance.toFloat())
            apply()
        }
    }

    private fun getWalletBalance(): Double {
        val sharedPref = getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)
        return sharedPref.getFloat("wallet_balance", 0f).toDouble()
    }
}