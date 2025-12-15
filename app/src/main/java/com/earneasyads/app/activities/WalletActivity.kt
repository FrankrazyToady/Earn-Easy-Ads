package com.earneasyads.app.activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R
import com.earneasyads.app.models.WithdrawalRequest
import com.earneasyads.app.models.WithdrawalResponse
import com.earneasyads.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletActivity : AppCompatActivity() {

    private lateinit var tvBalance: TextView
    private lateinit var btnWithdraw: Button
    private var walletBalance: Double = 0.0
    private val userId = 1 // TODO: Replace with actual user ID from auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        tvBalance = findViewById(R.id.tvBalance)
        btnWithdraw = findViewById(R.id.btnWithdraw)

        walletBalance = getWalletBalance()
        updateWalletUI()

        btnWithdraw.setOnClickListener {
            requestWithdrawal()
        }
    }

    private fun updateWalletUI() {
        tvBalance.text = "Wallet Balance: $%.2f".format(walletBalance)
    }

    private fun requestWithdrawal() {
        if (walletBalance <= 0) {
            Toast.makeText(this, "No balance to withdraw", Toast.LENGTH_SHORT).show()
            return
        }

        val withdrawalRequest = WithdrawalRequest(userId, walletBalance, "M-Pesa") // Example method
        val api = RetrofitClient.apiService
        api.requestWithdrawal(withdrawalRequest).enqueue(object : Callback<WithdrawalResponse> {
            override fun onResponse(
                call: Call<WithdrawalResponse>,
                response: Response<WithdrawalResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@WalletActivity, response.body()?.message ?: "Withdrawal successful", Toast.LENGTH_SHORT).show()
                    walletBalance = 0.0
                    saveWalletBalance(walletBalance)
                    updateWalletUI()
                } else {
                    Toast.makeText(this@WalletActivity, response.body()?.message ?: "Withdrawal failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WithdrawalResponse>, t: Throwable) {
                Toast.makeText(this@WalletActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
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