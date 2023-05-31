package com.ennovations.fitcrucoach.response

data class NotificationListResponse(
    val error_code: Int,
    val message: String,
    val data: List<Data>
) {
    data class Data(
        val id: Int,
        val type: String,

        val notified_user_id: Int,

        val data: String,

        val is_read: String,
        val coach_is_read: String,

        val read_at: Any? = null,

        val created_at: String,

        val updated_at: String
    )
}
