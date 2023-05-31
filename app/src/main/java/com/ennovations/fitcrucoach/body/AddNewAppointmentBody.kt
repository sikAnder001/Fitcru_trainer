package com.ennovations.fitcrucoach.body

data class AddNewAppointmentBody(
    val coach_time_slot_id: Int,
    val appointment_date: String,
    val user_id: Int,
    val note: String
)
