package com.earneasyads.app.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R

class ReferralActivity : AppCompatActivity() {

    private lateinit var tvReferralCode: TextView
    private lateinit var btnCopyCode: Button
    private lateinit var btnShareCode: Button

    private val referralCode = "EARNEASY123" // TODO: Generate dynamically per user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)

        tvReferralCode = findViewById(R.id.tvReferralCode)
        btnCopyCode = findViewById(R.id.btnCopyCode)
        btnShareCode = findViewById(R.id.btnShareCode)

        tvReferralCode.text = referralCode

        btnCopyCode.setOnClickListener { copyReferralCode() }
        btnShareCode.setOnClickListener { shareReferralCode() }
    }

    private fun copyReferralCode() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Referral Code", referralCode)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Referral code copied!", Toast.LENGTH_SHORT).show()
    }

    private fun shareReferralCode() {
        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            putExtra(android.content.Intent.EXTRA_TEXT, "Join Earn Easy Ads with my code: $referralCode")
            type = "text/plain"
        }
        startActivity(android.content.Intent.createChooser(shareIntent, "Share via"))
    }
}