package com.gs.realestate.util.imageSliderView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gs.realestate.R

class ImagesAdapter(private val mContext: Context, private val imagesList: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProperty: ImageView = itemView.findViewById(R.id.ivPropertyImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.item_image_slider, parent, false)
        return ImagesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item = imagesList[position]
        Glide.with(mContext)
            .load(item)
            .placeholder(R.drawable.gray_button_background)
            .error(R.drawable.gray_button_background)
            .into(holder.ivProperty)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}