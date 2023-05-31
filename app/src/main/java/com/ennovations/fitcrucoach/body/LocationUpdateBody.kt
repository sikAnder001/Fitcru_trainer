package com.ennovations.fitcrucoach.body

data class LocationUpdateBody(
    val country_id: Int,
    val state_id: Int,
    val city_id: Int,
    val pincode: String
)
