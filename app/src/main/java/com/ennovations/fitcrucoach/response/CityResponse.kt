package com.ennovations.fitcrucoach.response

data class CityResponse(
    val data: List<Data>,
    val error_code: Int,
    val message: String
) {
    data class Data(
        val id: Int,
        val name: String,
        val state_id: Int
    ) {
        override fun toString(): String {
            return name
        }
    }
}
