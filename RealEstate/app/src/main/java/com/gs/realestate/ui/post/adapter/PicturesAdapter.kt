package com.gs.realestate.ui.post.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gs.realestate.R

class PicturesAdapter(private val items: ArrayList<Uri>, val context: Context) :
    RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder>() {


    class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.im_upload)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.view_imageupload, parent, false)
        return PicturesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val imageUrl = items[position]
        Glide.with(context).asBitmap().load(imageUrl).placeholder(R.drawable.house)
            .into(holder.ivPicture)

    }

    override fun getItemCount(): Int {
        return if (items.size > 6) 6 else items.size
    }
}