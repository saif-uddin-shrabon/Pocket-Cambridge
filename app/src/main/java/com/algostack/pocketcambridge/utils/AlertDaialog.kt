package com.algostack.pocketcambridge.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.algostack.pocketcambridge.R

object AlertDaialog {
    fun noInternetConnectionAlertBox( context: Context){
        val view = LayoutInflater.from(context).inflate(R.layout.loadingbar, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val alert = builder.create()
        alert.setCancelable(false)






        alert.window?.setBackgroundDrawable(ColorDrawable(0))
        alert.show()



    }
}