package com.gs.realestate.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.gs.realestate.util.LoadingDialog

open class BaseFragment : Fragment() {

    private lateinit var mContext: Context
    private val progressDialog by lazy { LoadingDialog(mContext = mContext) }


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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContext = context
    }
}