package com.gs.realestate.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import com.gs.realestate.R

class LoadingDialog(mContext: Context) {

    private var dialog: CustomDialog
    private var tvTitle: TextView
    private var progressBar: ProgressBar

    /*
    * Method to show the loading indicator
    * */
    fun showLoading(loadingTitle: String = "") {
        tvTitle.text = loadingTitle
        dialog.show()
    }

    /*
    * Method to hide the loading indicator
    * */
    fun hideLoading() {
        dialog.dismiss()
    }


    init {
        val inflater = (mContext as Activity).layoutInflater
        val view = inflater.inflate(R.layout.layout_progress_bar, null)

        tvTitle = view.findViewById(R.id.tvProgressTitle)
        progressBar = view.findViewById(R.id.progressBar)

        // Custom Dialog initialization
        dialog = CustomDialog(mContext)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
    }


    class CustomDialog(mContext: Context) : Dialog(mContext, R.style.CustomDialogTheme) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(R.color.dialogBackground)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}