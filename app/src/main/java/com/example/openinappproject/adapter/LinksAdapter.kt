package com.example.openinappproject.adapter

import android.R.attr.text
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.openinappproject.R
import com.example.openinappproject.model.LinksModel


class LinksAdapter(private val context: Context) : RecyclerView.Adapter<LinksAdapter.Holder>() {
    private var linksList = listOf<LinksModel>()

    fun updateLinksList(list: List<LinksModel>) {
        linksList = list
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linkName = itemView.findViewById<TextView>(R.id.link_name_tv)
        val linkDate = itemView.findViewById<TextView>(R.id.link_date_tv)
        val copy = itemView.findViewById<ImageView>(R.id.links_copy_btn)
        val count = itemView.findViewById<TextView>(R.id.links_count_tv)
        val link = itemView.findViewById<TextView>(R.id.links_link_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_links,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return linksList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.link.text = linksList[position].link
        holder.linkDate.text = linksList[position].linkDate
        holder.linkName.text = linksList[position].linkName
        holder.count.text = linksList[position].count

        holder.copy.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label",linksList[position].link)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context,"Copied to clipboard",Toast.LENGTH_SHORT).show()
        }
    }
}