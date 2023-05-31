package com.ennovations.fitcrucoach.response

data class RemoveUnavailabilityDate(
    val error_code: Int,
    val message: String,
    val data: RemoveDate
) {
    data class RemoveDate(
        val id: Int,
        val coach_id: Int,
        val start_time: Any? = null,
        val end_time: Any? = null,
        val time_duration: Any? = null,
        val break_time_duration: Any? = null,
        val date: String? = null,
        val status: String? = null,
        val launch_start_time: Any? = null,
        val launch_end_time: Any? = null,
        val tea_break_start_time: Any? = null,
        val tea_break_end_time: Any? = null,
        val strdy_strt_time: Any? = null,
        val strdy_end_time: Any? = null,
    )
}

