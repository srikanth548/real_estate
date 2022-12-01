package com.gs.realestate.base

import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.util.LoadingDialog

open class BaseActivity : AppCompatActivity() {

    private val progressDialog by lazy { LoadingDialog(mContext = this) }


    /*
    * Method to load the custom loading indicator method
    * */
    fun showLoader(title: String = "") {
        progressDialog.showLoading(title)
    }

    /*
    * Method to dismiss the custom loading indicator method
    * */
    fun hideLoader() {
        progressDialog.hideLoading()
    }
}