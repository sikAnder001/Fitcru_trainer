package com.ennovations.fitcrucoach.response

data class ForgotPasswordResponse(
    val error_code: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val coach_email: String,
        val email_verification_code: String,
        val access_token: String
    )

}