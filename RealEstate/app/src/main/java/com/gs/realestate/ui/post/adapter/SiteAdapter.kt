package com.gs.realestate.ui.post.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.gs.realestate.R

class SiteAdapter (val items : ArrayList<String>, val context: Context) : BaseAdapter() {

    var cont: Context;

    init {
        cont = context
    }
    private var layoutInflater: LayoutInflater? = null
    private lateinit var highlightext: CheckBox



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
            convertView = layoutInflater!!.inflate(R.layout.view_site, null)
            highlightext = convertView.findViewById(R.id.cb_checknearby)
        }
        val name = items[position]
        //highlightext.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_aeroplane,0,0,0)
        highlightext.text = name
        return convertView
    }
}

