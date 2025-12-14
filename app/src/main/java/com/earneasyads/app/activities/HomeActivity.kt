package com.earneasyads.app.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.earneasyads.app.R
import com.earneasyads.app.adapters.AdsAdapter
import com.earneasyads.app.models.AdItem

class HomeActivity : AppCompatActivity() {

    private lateinit var tvWalletBalance: TextView
    private lateinit var rvAds: RecyclerView
    private var walletBalance: Double = 0.0
    private lateinit var adsList: MutableList<AdItem>
    private lateinit var adsAdapter: AdsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvWalletBalance = findViewById(R.id.tvWalletBalance)
        rvAds = findViewById(R.id.rvAds)

        walletBalance = getWalletBalance()
        updateWalletUI()

        setupAdsFeed()
    }

    private fun setupAdsFeed() {
        adsList = mutableListOf(
            AdItem(1, "Watch this video ad", 0.5, "network"),
            AdItem(2, "Check out this app", 1.0, "internal"),
            AdItem(3, "Try this game", 0.75, "network")
        )

        adsAdapter = AdsAdapter(adsList) { adItem ->
            // TODO: Show rewarded ad or process internal ad
            walletBalance += adItem.reward
            saveWalletBalance(walletBalance)
            updateWalletUI()
            Toast.makeText(this, "You earned $${adItem.reward}!", Toast.LENGTH_SHORT).show()
        }

        rvAds.layoutManager = LinearLayoutManager(this)
        rvAds.adapter = adsAdapter
    }

    private fun updateWalletUI() {
        tvWalletBalance.text = "Wallet: $%.2f".format(walletBalance)
    }

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