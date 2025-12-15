package com.earneasyads.app.models

data class WithdrawalRequest(
    val userId: Int,
    val amount: Double,
    val method: String // e.g., "PayPal", "M-Pesa", "Crypto"
)