package com.ennovations.fitcrucoach.response

data class LanguagesResponse(
    val Languages: List<Data>,
    val error_code: Int,
    val message: String
) {
    data class Data(
        val id: Int,
        val name: String
    ) {
        override fun toString(): String {
            return name
        }

    }
}
