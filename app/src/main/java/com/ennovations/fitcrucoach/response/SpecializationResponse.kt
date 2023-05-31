package com.ennovations.fitcrucoach.response

data class SpecializationResponse(
    val Specializations: List<Data>,
    val error_code: Int,
    val message: String
) {
    data class Data(
        val id: Int,
        val name: String,
        val image: String,
        val image_url: String
    ) {
        override fun toString(): String {
            return name
        }

    }
}
