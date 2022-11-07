package com.gs.realestate.ui.home.allproperties

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gs.realestate.R
import com.gs.realestate.network.Properties

class PropertyListAdapter (val items : ArrayList<Properties>, val context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    var cont: Context;

    init {
        cont = context
    }
    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_propertyitem, parent, false))
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_propertyitem, parent, false)
        return MyViewHolder(itemView)
    }



    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var propertiesObj = items.get(position)
       val acres = propertiesObj.details?.get(0)?.value + " "+ propertiesObj.details?.get(0)?.unit
        val amount = propertiesObj.details?.get(1)?.value+ " "+ propertiesObj.details?.get(1)?.unit
        holder.amount.text = amount
        holder.landDescription.text = acres
        holder.heartCount.text = "" + propertiesObj.favCount
        holder.viewCount.text = "" + propertiesObj.viewCount
        holder.expiryDate.text = "Expires : " + propertiesObj.expiryDate
        holder.location.text = propertiesObj.location
        val image = propertiesObj.imageUrl.orEmpty()
//        propertiesObj.imageUrl?.let { image -> {
            println("image " + image)

            if(!image.startsWith("https")) {
                propertiesObj.imageUrl = "https://rightmycdn.azureedge.net/" + propertiesObj.imageUrl
                println("image url" + propertiesObj.imageUrl)
            }
            Glide.with(cont).asBitmap().load(propertiesObj.imageUrl).into(holder.backgroundImage);

//            Picasso.get().load("https://img.freepik.com/premium-photo/astronaut-outer-open-space-planet-earth-stars-provide-background-erforming-space-planet-earth-sunrise-sunset-our-home-iss-elements-this-image-furnished-by-nasa_150455-16829.jpg?w=740").resize(50, 50).
//            centerCrop().placeholder(R.drawable.house)
//                .error(R.drawable.home)
//                .into(holder.backgroundImage);
        //} }

    }
}

class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    var heartCount: TextView = view.findViewById(R.id.tv_heartcount)
    var viewCount: TextView = view.findViewById(R.id.tv_viewcount)
    var amount: TextView = view.findViewById(R.id.tv_amount)
    var expiryDate: TextView = view.findViewById(R.id.tv_expirydate)
    var landDescription: TextView = view.findViewById(R.id.land_description)
    var location: TextView = view.findViewById(R.id.location)
    var backgroundImage: ImageView = view.findViewById(R.id.rl_background)

}