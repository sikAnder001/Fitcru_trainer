package com.ennovations.fitcrucoach.response

data class GetCertificateResponse(
    val error_code: Int,
    val message: String,
    val data: List<Data>,
) {
    data class Data(
        val id: Int,

        val coach_id: Int,

        val name: String,
        val image: String? = null,
        val description: String,
        val comment: Any? = null,
        val status: String,

        val image_url: String? = null,

        val certificate_name: String,

        val certi_reject_reason: String? = null
    )
}
