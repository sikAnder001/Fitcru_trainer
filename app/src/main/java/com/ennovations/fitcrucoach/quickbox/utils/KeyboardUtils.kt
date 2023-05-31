package com.ennovations.fitcrucoach.quickbox.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.ennovations.fitcrucoach.utility.SessionManager


fun showKeyboard(editText: EditText) {
    val imm = SessionManager.getInstance()
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun hideKeyboard(view: View) {
    val imm = SessionManager.getInstance()
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}