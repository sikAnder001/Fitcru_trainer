package com.ennovations.fitcrucoach.response

data class CoachDetailResponse(
    val error_code: Int,
    val message: String,
    val data: Data,
    val user_data: Data
) {
    data class Data(
        val access_token: String,
        val created_at: String,
        val device_token: Any,
        val dob: String,
        val email: String,
        val email_verified_at: Any,
        val gender: String,
        val id: Int,
        val image: String,
        val image_url: String,
        val name: String,
        val otp: String,
        val pending: Int,
        val phone_number: String,
        val provider_id: Any,
        val provider_name: Any,
        val role: Int,
        val status: Int,
        val updated_at: String,
        val usertype: Int,
        val bmi_calculation: String,

        val social_id: String,

        val login_by: String,

        )
}
