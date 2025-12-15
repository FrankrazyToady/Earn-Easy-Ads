package com.earneasyads.app.network

import com.earneasyads.app.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/auth/signup")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>

    @POST("api/withdraw")
    fun requestWithdrawal(@Body withdrawalRequest: WithdrawalRequest): Call<WithdrawalResponse>
}