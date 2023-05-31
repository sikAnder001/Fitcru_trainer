package com.ennovations.fitcrucoach.response

data class AvailableTimeSlotResponse(
    val error_code: Int,
    val message: String,
    val data: AvailableData
) {
    data class AvailableData(

        val coach_details: CoachData,

        val available_time_slots: List<AvailableTimeSlot>,
    ) {
        data class CoachData(
            val id: Int,
            val coach_name: String,
            val coach_email: String,
            val email_verification_code: String,
            val email_verified_at: String,
            val phone_number: String,
            val otp: String? = null,
            val image: String,
            val image_url: String,
            val dob: String,
            val doj: String,
            val coach_qualifications: String,
            val coach_biodata: String,
            val language_ids: String,
            val coach_specialization_ids: String,
            val coach_category_id: String,
            val gym_association_ids: String,
            val coach_slab_type: String,
            val adhar_card_no: String,
            val adhar_card_front_image: String,
            val aadhar_front_image_url: String? = null,
            val adhar_card_back_image: String? = null,
            val aadhar_back_image_url: String? = null,
            val passport_no: String? = null,
            val gender: String? = null,
            val status: String? = null,
            val working_day: String? = null,
            val working_hours: String? = null,
            val no_of_slots: String? = null,
            val coach_instagram_link: String? = null,
            val commission_percentage_per_client: String? = null,
            val fixed_remuneration: String? = null,
            val monthly_pay_out: String? = null,
            val mondy_to_frdy_strt_time: String? = null,
            val mondy_to_frdy_end_time: String? = null,
            val strdy_end_time: String? = null,
            val strdy_strt_time: String? = null,
        )

        data class AvailableTimeSlot(
            val id: Int,
            val availability_id: Int,
            val slot_start_time: String,
            val slot_end_time: String,
            val coach_id: Int,
            val is_available: String
        )
    }
}