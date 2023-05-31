package com.ennovations.fitcrucoach.body

data class LoginBody(
    val coach_email: String,
    val password: String,
    val deviceToken: String,
    val deviceId: String,
    val deviceType: Int
)