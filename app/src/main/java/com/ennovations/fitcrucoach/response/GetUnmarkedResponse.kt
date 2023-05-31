package com.ennovations.fitcrucoach.response

data class GetUnmarkedResponse(
    val error_code: Int,
    val message: String,
    val data: List<String>,
    val appointments: List<String>,
)
