package com.earneasyads.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.earneasyads.app.R
import com.earneasyads.app.models.AdItem

class AdsAdapter(
    private val adsList: List<AdItem>,
    private val onWatchAdClick: (AdItem) -> Unit
) : RecyclerView.Adapter<AdsAdapter.AdViewHolder>() {

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAdTitle: TextView = itemView.findViewById(R.id.tvAdTitle)
        val btnWatch: Button = itemView.findViewById(R.id.btnWatchAd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false)
        return AdViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val adItem = adsList[position]
        holder.tvAdTitle.text = adItem.title
        holder.btnWatch.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Watching: ${adItem.title}", Toast.LENGTH_SHORT).show()
            onWatchAdClick(adItem)
        }
    }

    override fun getItemCount(): Int = adsList.size
}