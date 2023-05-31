package com.ennovations.fitcrucoach.response

data class LocationUpdateResponse(
    val data: Data,
    val error_code: Int,
    val message: String
) {

    data class Data(
        val id: Int,
        val coach_id: Int,
        val country_id: Int,
        val state_id: Int,
        val city_id: Int,
        val location_name: String,
        val pincode: String,
    )
}