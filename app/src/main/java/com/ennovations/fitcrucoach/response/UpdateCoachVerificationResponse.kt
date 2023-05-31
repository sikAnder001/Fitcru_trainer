package com.ennovations.fitcrucoach.response

data class UpdateCoachVerificationResponse(
    val error_code: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val coach_name: String,
        val coach_email: String,
        val email_verification_code: String,
        val email_verified_at: String,
        val phone_number: String,
        val otp: String,
        val image: String,
        val image_url: String,
        val dob: String,
        val doj: String,
        val coach_qualifications: String,
        val coach_biodata: String,
        val language_ids: String,
        val coach_specialization_ids: String,
        val coach_category_id: Int,
        val gym_association_ids: String,
        val coach_slab_type: String,
        val adhar_card_no: String,
        val adhar_card_front_image: String,
        val aadhar_front_image_url: String,
        val adhar_card_back_image: String,
        val aadhar_back_image_url: String,
        val passport_no: String,
        val gender: String,
        val status: String,
        val working_day: String,
        val working_hours: String,
        val no_of_slots: String,
        val available_slots: String,
        val coach_instagram_link: Any,
        val commission_percentage_per_client: Any,
        val fixed_remuneration: Any

    )
}