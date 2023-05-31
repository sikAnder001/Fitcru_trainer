package com.ennovations.fitcrucoach.response

data class LoginResponse(
    val `data`: Data,
    val error_code: Int,
    val message: String
) {
    data class Data(
        val access_token: String,
        val adhar_card_back_image: String,
        val adhar_card_front_image: String,
        val adhar_card_no: String,
        val available_slots: String,
        val coach_biodata: String,
        val coach_category_id: Int,
        val coach_email: String,
        val coach_instagram_link: Any,
        val coach_name: String,
        val coach_qualifications: String,
        val coach_slab_type: String,
        val coach_specialization_ids: String,
        val commission_percentage_per_client: Any,
        val discount_percent: String,
        val dob: String,
        val doj: String,
        val email_verification_code: Any,
        val fixed_remuneration: Any,
        val gender: String,
        val gym_association_ids: String,
        val id: Int?,
        val image: String,
        val image_url: String? = null,
        val languagess: String,
        val no_of_slots: String,
        val otp: Any,
        val passport_no: Any,
        val phone_number: String,
        val status: String,
        val working_day: Any,
        val working_hours: Any,
        val quick_blox_id: String?,
        val quick_blox_status: String

    )
}