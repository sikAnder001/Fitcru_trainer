package com.ennovations.fitcrucoach.body

data class CancelAppointmentBody(
    val coach_time_slot_id: Int,
    val appointment_id: Int,
    val user_id: Int
)
