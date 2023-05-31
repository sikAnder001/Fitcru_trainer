package com.ennovations.fitcrucoach.response

data class LocationDetailsResponse(
    val error_code: Int,
    val message: String,
    val locations: LocationDetails

) {
    data class LocationDetails(
        val country: String,
        val state: String,
        val city: String,
        val pincode: String

    )
}
