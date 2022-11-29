package com.gs.realestate.ui.post.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gs.realestate.R
import com.gs.realestate.network.Properties

class ImageAdapter (val items : ArrayList<Uri>, val context: Context) : BaseAdapter() {

    var cont: Context;

    init {
        cont = context
    }
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView



    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
       return items.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.view_imageupload, null)
            imageView = convertView.findViewById(R.id.im_upload)
        }
        var imageUrl = items.get(position)



//        propertiesObj.imageUrl?.let { image -> {
        Log.i("image {}",imageUrl.toString())
//
//        if(!imageUrl.startsWith("https")) {
//            imageUrl = "https://rightmycdn.azureedge.net/" + imageUrl
//            Log.i("image url {}" , imageUrl)
//        }
        Glide.with(cont).asBitmap().load(imageUrl).placeholder(R.drawable.house).into(imageView);

        return convertView
    }
}

class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var backgroundImage: ImageView = view.findViewById(R.id.im_upload)

}