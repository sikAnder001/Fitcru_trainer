package com.ennovations.fitcrucoach.response

data class ChatListResponse(
    val data: List<Data>,
    val error_code: Int,
    val message: String
) {
    data class Data(
        val created_at: String,
        val updated_at: String,
        val id: Int,
        val image_url: String,
        val last_message: String,
        val name: String
    )
}