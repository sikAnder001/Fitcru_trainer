package com.ennovations.fitcrucoach.response

data class ReadNotificationResponse(
    val error_code: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Long,
        val type: String,

        val notified_user_id: Long,

        val data: String,

        val is_read: String,

        val read_at: String,

        )
}

