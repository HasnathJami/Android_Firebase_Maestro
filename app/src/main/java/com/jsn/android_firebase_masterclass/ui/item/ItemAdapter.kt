package com.jsn.android_firebase_masterclass.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jsn.android_firebase_masterclass.R
import com.jsn.android_firebase_masterclass.model.Item

class ItemAdapter(private val items: List<Item>, private val onClick: (Item) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_name)
        val descriptionTextView: TextView = view.findViewById(R.id.item_description)
        val idTextView: TextView = view.findViewById(R.id.item_id)
        val itemImage: ImageView = view.findViewById(R.id.item_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description
        holder.itemView.setOnClickListener { onClick(item) }
        holder.idTextView.text = item.id

        //Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.itemImage)
    }

    override fun getItemCount() = items.size
}
