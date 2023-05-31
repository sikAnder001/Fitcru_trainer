package com.ennovations.fitcrucoach.response

data class VerificationDetailsResponse(
    val error_code: Int,
    val message: String,
    val Verifications: VerificationDetailsData
) {
    data class VerificationDetailsData(
        val adhar_card_no: String,
        val coach_instagram_link: String,
        val no_of_slots: String,
        val aadhar_front_image_url: String,
        val adhar_card_front_image: String,
        val adhar_card_back_image: String,
        val aadhar_back_image_url: String,

        )
}

