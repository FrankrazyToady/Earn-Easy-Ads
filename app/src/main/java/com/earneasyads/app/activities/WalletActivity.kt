package com.earneasyads.app.activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R

class WalletActivity : AppCompatActivity() {

    private lateinit var tvBalance: TextView
    private lateinit var btnWithdraw: Button
    private var walletBalance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        tvBalance = findViewById(R.id.tvBalance)
        btnWithdraw = findViewById(R.id.btnWithdraw)

        walletBalance = getWalletBalance()
        updateWalletUI()

        btnWithdraw.setOnClickListener {
            withdrawBalance()
        }
    }

    private fun updateWalletUI() {
        tvBalance.text = "Wallet Balance: $%.2f".format(walletBalance)
    }

    private fun withdrawBalance() {
        if (walletBalance > 0) {
            Toast.makeText(this, "Withdrawal request submitted!", Toast.LENGTH_SHORT).show()
            walletBalance = 0.0
            saveWalletBalance(walletBalance)
            updateWalletUI()
        } else {
            Toast.makeText(this, "No balance to withdraw", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getWalletBalance(): Double {
        val sharedPref = getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)
        return sharedPref.getFloat("wallet_balance", 0f).toDouble()
    }

    private fun saveWalletBalance(balance: Double) {
        val sharedPref = getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putFloat("wallet_balance", balance.toFloat())
            apply()
        }
    }
}