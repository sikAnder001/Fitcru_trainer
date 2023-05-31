package com.ennovations.fitcrucoach.response

data class StateResponse(
    val data: List<Data>,
    val error_code: Int,
    val message: String
) {

    data class Data(
        val country_id: Int,
        val id: Int,
        val name: String
    ) {
        override fun toString(): String {
            return name
        }
    }
}