package com.example.openinappproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.openinappproject.R
import com.example.openinappproject.model.AnalyticsModel

class AnalyticsAdapter : RecyclerView.Adapter<AnalyticsAdapter.Holder>() {
    private var analyticsList = listOf<AnalyticsModel>()
    fun updateAnalyticsList(list : List<AnalyticsModel>) {
        analyticsList = list
        notifyDataSetChanged()
    }
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIcon = itemView.findViewById<ImageView>(R.id.analytics_img)
        val text_first = itemView.findViewById<TextView>(R.id.analytics_tv_first)
        val text_second = itemView.findViewById<TextView>(R.id.analytics_tv_second)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_analytics,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return analyticsList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.imageIcon.setImageResource(analyticsList[position].icon)
        holder.text_first.text = analyticsList[position].textFirst
        holder.text_second.text = analyticsList[position].textSecond
    }
}