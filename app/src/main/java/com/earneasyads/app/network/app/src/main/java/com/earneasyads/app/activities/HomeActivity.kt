package com.earneasyads.app.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // TODO: Load ads using AdMob or other ad network
        Toast.makeText(this, "Welcome to Earn Easy Ads!", Toast.LENGTH_SHORT).show()
    }
}