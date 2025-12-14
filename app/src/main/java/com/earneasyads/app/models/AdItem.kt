package com.earneasyads.app.models

data class AdItem(
    val id: Int,
    val title: String,
    val reward: Double,
    val adType: String // e.g., "internal" or "network"
)