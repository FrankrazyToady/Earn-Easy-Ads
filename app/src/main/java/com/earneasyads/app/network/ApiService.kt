package com.earneasyads.app.network

import com.earneasyads.app.models.LoginRequest
import com.earneasyads.app.models.LoginResponse
import com.earneasyads.app.models.SignupRequest
import com.earneasyads.app.models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/auth/signup")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>
}