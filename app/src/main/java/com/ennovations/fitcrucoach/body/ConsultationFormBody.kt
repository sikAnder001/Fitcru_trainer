package com.ennovations.fitcrucoach.body

data class ConsultationFormBody(
    val update: Boolean,
    val user_id: Int,
    val questions: ArrayList<Question>,
    val goals: ArrayList<Goals>,
    val edit: ArrayList<Edit>
) {
    data class Question(
        val id: Int,
        val ques: String,
        val answer: String? = null,
        val overall_calories: String? = null,
        val unit: String? = null,
        val consume_carbs: Int? = null,
        val consume_protein: Int? = null,
        val consume_fat: Int? = null,
        val calorie_burn: String? = null,
        val steps: Int? = null,
        val water: Int? = null,
    )

    data class Goals(
        val id: Int,
        val ques: String,
        val answer: String? = null,
        val custom: String? = null,
        val target_date: String? = null,
        val target_value: Int? = null,
    )

    data class Edit(val id: Int, val ques: String, val answer: String, val ifYes: String)
}


