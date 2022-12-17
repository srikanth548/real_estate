package com.gs.realestate.ui.home.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gs.realestate.R
import com.gs.realestate.network.models.notification.Notification

class NotificationListAdapter(
    private val mContext: Context,
    private val notificationsList: List<Notification>
) : RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder>() {


    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNotificationTitle: TextView = itemView.findViewById(R.id.tvNotificationTitle)
        val tvNotificationDescription: TextView =
            itemView.findViewById(R.id.tvNotificationDescription)
        val tvNotificationTime: TextView = itemView.findViewById(R.id.tvNotificationTime)


        fun bind(item: Notification) {
            tvNotificationTitle.text = item.title
            tvNotificationDescription.text = item.message
            tvNotificationTime.text = item.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notificationsList[position])
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }
}