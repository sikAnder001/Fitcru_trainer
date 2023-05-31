package com.ennovations.fitcrucoach.response

data class SpecializationDetailsResponse(
    val error_code: Int,
    val message: String,
    val Specializations: List<SpecializationDetailsData>? = null
) {
    data class SpecializationDetailsData(
        val language_ids: String? = null,
        val languages: List<LanguageData>,
        val coach_specialization_ids: String? = null,
        val specializations: List<SpecializationsData>,
        val gym_association_ids: String? = null,
        val gymAassociations: List<AssociationData>,
        val coach_qualifications: String
    ) {
        data class LanguageData(
            val id: Int,
            val name: String
        )

        data class SpecializationsData(
            val id: Int,
            val name: String,
            val image: String? = null,
            val image_url: String? = null
        )

        data class AssociationData(
            val id: Int,
            val name: String
        )
    }


}
