package com.gs.realestate.ui.home.allproperties

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gs.realestate.R
import com.gs.realestate.network.Properties
import com.gs.realestate.ui.home.property.PropertyDetailsActivity

class PropertyListAdapter(private val items: ArrayList<Properties>, val context: Context) :
    RecyclerView.Adapter<MyViewHolder>() {

    var cont: Context = context

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_propertyitem, parent, false)
        return MyViewHolder(itemView)
    }


    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val propertiesObj = items[position]
        val acres = propertiesObj.details?.get(0)?.value + " " + propertiesObj.details?.get(0)?.unit
        val amount =
            propertiesObj.details?.get(1)?.value + " " + propertiesObj.details?.get(1)?.unit
        holder.amount.text = amount
        holder.landDescription.text = acres
        holder.heartCount.text = propertiesObj.favCount.toString()
        holder.viewCount.text = propertiesObj.viewCount.toString()
        holder.expiryDate.text = "Expires : ${propertiesObj.expiryDate}"
        holder.location.text = propertiesObj.location
        val image = propertiesObj.imageUrl.orEmpty()
        println("image $image")

        if (!image.startsWith("https")) {
            propertiesObj.imageUrl = "https://rightmycdn.azureedge.net/" + propertiesObj.imageUrl
            println("image url" + propertiesObj.imageUrl)
        }

        Glide.with(cont)
            .asBitmap()
            .load(propertiesObj.imageUrl)
            .placeholder(R.drawable.house)
            .into(holder.backgroundImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PropertyDetailsActivity::class.java)
            intent.putExtra("EXTRA_PROPERTY_DETAILS", propertiesObj)
            context.startActivity(intent)
        }

    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    var heartCount: TextView = view.findViewById(R.id.tv_heartcount)
    var viewCount: TextView = view.findViewById(R.id.tv_viewcount)
    var amount: TextView = view.findViewById(R.id.tv_amount)
    var expiryDate: TextView = view.findViewById(R.id.tv_expirydate)
    var landDescription: TextView = view.findViewById(R.id.land_description)
    var location: TextView = view.findViewById(R.id.location)
    var backgroundImage: ImageView = view.findViewById(R.id.rl_background)

}