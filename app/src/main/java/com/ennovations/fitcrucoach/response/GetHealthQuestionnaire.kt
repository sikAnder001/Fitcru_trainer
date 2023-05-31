package com.ennovations.fitcrucoach.response

import com.google.gson.annotations.SerializedName

data class GetHealthQuestionnaire(
    val error_code: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val user_id: Int,
        val questions: Questions
    ) {
        data class Questions(
            val question_data: ArrayList<QuestionData>,
            @SerializedName("Image") val image: ArrayList<Image>
        ) {
            data class QuestionData(val id: Int, val name: String, val answer: ArrayList<String>)
            data class Image(val image_url: String)
        }
    }
}
