package com.ennovations.fitcrucoach.quickbox.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.ennovations.fitcrucoach.R


class ProgressDialogUtil {
    private var mDialog: Dialog? = null
    private var c: Context? = null

    companion object {
        private var progressDialog: ProgressDialogUtil? = null
        fun getInstance(): ProgressDialogUtil {
            if (progressDialog == null) {
                progressDialog = ProgressDialogUtil()
            }
            return progressDialog as ProgressDialogUtil
        }
    }

    fun showProgress(context: Context, cancelable: Boolean) {
        if (mDialog == null || context != c) {
            c = context
            mDialog = Dialog(context)
            mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog?.setContentView(R.layout.pop_up_custom_progress)
            mDialog?.setCancelable(cancelable)
            mDialog?.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mDialog?.setCanceledOnTouchOutside(cancelable)
        }
        mDialog?.show()
    }

    fun hideProgress() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog?.dismiss()
            mDialog = null
        }
    }
}