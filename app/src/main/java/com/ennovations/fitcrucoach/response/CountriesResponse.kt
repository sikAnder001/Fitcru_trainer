package com.ennovations.fitcrucoach.response

data class CountriesResponse(
    val data: List<Data>,
    val error_code: Int,
    val message: String
) {
    data class Data(
        val id: Int,
        val name: String,
        val phonecode: Int,
        val shortname: String
    ) {
        override fun toString(): String {
            return name
        }
    }
}
