package com.example.mobile_lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResourceRecyclerAdapter(val list: MutableList<MediaResource>, val itemClick: (MediaResource) -> Unit) : RecyclerView.Adapter<ResourceRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.resource_list_item, parent, false)
        return ItemViewHolder(row, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    inner class ItemViewHolder(itemView: View, val itemClick: (MediaResource) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_title)

        fun bindItem(mediaResource: MediaResource) {
            title.text = mediaResource.title
            itemView.setOnClickListener {itemClick(mediaResource)}
        }
    }
}