package com.ennovations.fitcrucoach.body

data class LastMessage(
    val user_id: Int,
    val coach_id: Int,
    val last_message: String,
    val sender_id: Int,
    val receiver_id: Int
)
