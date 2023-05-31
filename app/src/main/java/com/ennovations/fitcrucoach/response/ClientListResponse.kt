package com.ennovations.fitcrucoach.response

data class ClientListResponse(
    val error_code: Int,

    val message: String,

    val Clients: List<Client>,

    val filters: FilterData
) {

    data class Client(
        val user_id: Int? = null,

        val name: String? = null,
        val image: String? = null,

        val image_url: String? = null,

        val join: String? = null,
        val expire: String? = null,
        val status: String? = null
    )

    data class FilterData(
        val fsd: String,

        val fed: String,

        val lmr: String,

        val mug: String
    )
}
