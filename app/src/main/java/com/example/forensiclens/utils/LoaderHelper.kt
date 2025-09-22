package com.example.forensiclens.utils

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import com.example.forensiclens.R



object LoaderHelper {
    private var progressDialog: ProgressDialog? = null

    fun showLoader(context: Context, message:String = "Loading... "){
        if (progressDialog==null){
            progressDialog = ProgressDialog(context).apply {
                setCancelable(false)
                setMessage(message)
                show()
            }
        } else if (progressDialog?.isShowing == false){
            progressDialog?.setMessage(message)
            progressDialog?.show()
        }
    }

    fun hideLoader(){
        progressDialog?.dismiss()
        progressDialog = null
    }
}