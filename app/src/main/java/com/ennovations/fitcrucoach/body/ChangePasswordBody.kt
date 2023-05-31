package com.ennovations.fitcrucoach.body

data class ChangePasswordBody(
    val old_password: String,
    val password: String,
    val confirm_password: String
)
