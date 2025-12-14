package com.earneasyads.app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.earneasyads.app.R
import com.earneasyads.app.models.LoginRequest
import com.earneasyads.app.models.LoginResponse
import com.earneasyads.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if token exists, skip login if already logged in
        val token = getAuthToken()
        if (token.isNotEmpty()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)

        btnLogin.setOnClickListener { loginUser() }
        btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        val api = RetrofitClient.apiService
        val call = api.login(LoginRequest(email, password))
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    saveAuthToken(token) // Save token
                    Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // SharedPreferences helpers
    private fun saveAuthToken(token: String) {
        val sharedPref = getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("auth_token", token)
            apply()
        }
    }

    private fun getAuthToken(): String {
        val sharedPref = getSharedPreferences("EarnEasyAdsPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("auth_token", "") ?: ""
    }
}