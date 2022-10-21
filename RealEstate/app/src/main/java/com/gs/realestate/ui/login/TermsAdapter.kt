package com.gs.realestate.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gs.realestate.R

class TermsAdapter(
    private val context: Context,
    private val arrayList: Array<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(i: Int): Any {
        return arrayList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_terms, viewGroup, false)
        }
        val terms = getItem(i) as String
        val tvName = convertView!!.findViewById(R.id.tv_terms) as TextView
        tvName.text = terms
        return convertView
    }

}