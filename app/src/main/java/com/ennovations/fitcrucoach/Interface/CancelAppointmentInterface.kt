package com.ennovations.fitcrucoach.Interface

interface CancelAppointmentInterface {

    fun onCancelClick(
        userId: Int,
        coachSlotTimeId: Int,
        appointmentId: Int
    )
}