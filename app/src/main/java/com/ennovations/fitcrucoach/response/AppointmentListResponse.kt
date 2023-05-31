package com.ennovations.fitcrucoach.response

data class AppointmentListResponse(
    val error_code: Int,
    val message: String,
    val CoachAppointment: List<CoachAppointmentData>? = null
) {
    data class CoachAppointmentData(

        val id: Int,

        val availabilityID: Int,

        val availability_id: String,

        val slot_start_time: String? = null,
        val slot_end_time: String,

        val coach_id: Int,

        val is_available: String,

        val slot_strdy_strt_time: String? = null,

        val slot_strdy_end_time: Any? = null,
        val appointment_detail: AppointmentDetail
    ) {
        data class AppointmentDetail(
            val id: Int,

            val appointment_no: String,

            val user_id: Int,

            val coach_time_slot_id: Int,

            val appointment_date: String,

            val appointment_day_name: String,

            val note: String,

            val is_book: String,
            val user_detail: UserDetail
        ) {
            data class UserDetail(
                val id: Int,
                val name: String,
                val image: String,

                val image_url: String,

                val phone_number: String,

                val quick_blox_id: String
            )
        }
    }
}