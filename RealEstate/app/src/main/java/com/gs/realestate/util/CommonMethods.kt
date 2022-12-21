package com.gs.realestate.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.util.*


object CommonMethods {

    /*
    * Method to generate random UUID
    * */
    fun getRandomUUID(): String{
        return UUID.randomUUID().toString()
    }


    /*
    * Method to get file path from Uri
    * */
    fun getRealPathFromURI(mContext: Context, contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            contentUri?.let { mContext.contentResolver.query(it, proj, null, null, null) }
        if (cursor?.moveToFirst() == true) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(columnIndex)
        }
        cursor?.close()
        return res
    }

    fun filePathFromCaches(contentUri: Uri): String? {
        val filePath = contentUri.path
        return filePath
    }
}