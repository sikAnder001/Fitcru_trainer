package com.ennovations.fitcrucoach.response

data class GetConsultationResponse(
    val error_code: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,

        val coach_id: Int,

        val user_id: Int,

        val questions: Questions

    ) {
        data class Questions(
            val user_id: Int,
            val questions: List<Questions>,
            val edit: List<Questions>,
            val goals: List<Questions>
        ) {
            data class Questions(
                val id: Int,
                val ques: String,
                val ifYes: String? = null,
                val answer: String? = null,
                val custom: String? = null,
                val overall_calories: String? = null,
                val unit: String? = null,
                val consume_carbs: Int? = null,
                val consume_protein: Int? = null,
                val consume_fat: Int? = null,
                val calorie_burn: String? = null,
                val target_date: String? = null,
                val target_value: Int? = null,
                val steps: Int? = null,
                val water: Int? = null
            )

        }
    }

}

