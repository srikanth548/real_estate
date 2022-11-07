package com.gs.realestate.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.gs.realestate.R

object SnackBarToast {

    fun showErrorSnackBar(view: View, message: String){
        val snackBar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackBar.setActionTextColor(Color.WHITE)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.RED)
        val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }

    fun failedCall(context: Context){
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(context)
        builderSingle.setTitle("Error")
        builderSingle.setMessage(context.getString(R.string.errorLogin))
        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
        builderSingle.show()

    }
}