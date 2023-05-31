package com.ennovations.fitcrucoach.Network

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import com.ennovations.fitcrucoach.R

class CustomProgressLoading(var context: Context) {

    var dialog: Dialog? = null

    private var progressBar: ProgressBar? = null

    fun showProgress() {

        if (dialog == null) {

            dialog = Dialog(context)

            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog?.setContentView(R.layout.custom_loader)

            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            progressBar = dialog?.findViewById(R.id.progressbar)
        }

        if (dialog != null && !dialog!!.isShowing) {

            dialog?.setCancelable(false)

            dialog?.setCanceledOnTouchOutside(false)

            try {

                dialog?.show()

            } catch (e: Exception) {

                dialog!!.dismiss()
            }
        }
    }

    fun hideProgress() {

        if (dialog != null && dialog!!.isShowing) {

            dialog!!.dismiss()
        }
    }
}