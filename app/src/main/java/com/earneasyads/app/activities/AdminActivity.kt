package com.earneasyads.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R
import com.earneasyads.app.models.AdItem

class AdminActivity : AppCompatActivity() {

    private lateinit var etAdTitle: EditText
    private lateinit var etReward: EditText
    private lateinit var btnUploadAd: Button

    // In-memory list of internal ads for now
    private val internalAdsList: MutableList<AdItem> = mutableListOf()
    private var adIdCounter = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        etAdTitle = findViewById(R.id.etAdTitle)
        etReward = findViewById(R.id.etReward)
        btnUploadAd = findViewById(R.id.btnUploadAd)

        btnUploadAd.setOnClickListener {
            uploadAd()
        }
    }

    private fun uploadAd() {
        val title = etAdTitle.text.toString().trim()
        val rewardText = etReward.text.toString().trim()

        if (title.isEmpty() || rewardText.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val reward = rewardText.toDoubleOrNull()
        if (reward == null) {
            Toast.makeText(this, "Enter a valid reward amount", Toast.LENGTH_SHORT).show()
            return
        }

        val adItem = AdItem(adIdCounter++, title, reward, "internal")
        internalAdsList.add(adItem)

        Toast.makeText(this, "Ad uploaded successfully!", Toast.LENGTH_SHORT).show()
        etAdTitle.text.clear()
        etReward.text.clear()
    }
}