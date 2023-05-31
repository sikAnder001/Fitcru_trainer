package com.ennovations.fitcrucoach.utility

import android.util.Patterns

object Helper {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}