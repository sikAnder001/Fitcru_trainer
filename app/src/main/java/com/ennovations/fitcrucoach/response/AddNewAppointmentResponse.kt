package com.ennovations.fitcrucoach.response

data class AddNewAppointmentResponse(
    val error_code: Int,
    val message: String,
    val data: AddAppointmentData
) {
    data class AddAppointmentData(
        val id: Int,
        val appointment_no: String,
        val user_id: String,
        val coach_time_slot_id: String,
        val appointment_date: String,
        val appointment_day_name: String,
        val note: String,
        val is_book: String
    )
}