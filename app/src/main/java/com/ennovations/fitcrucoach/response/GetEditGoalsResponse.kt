package com.ennovations.fitcrucoach.response

data class GetEditGoalsResponse(
    val error_code: Int,
    val message: String,
    val data: GetEditGoalData,
) {
    data class GetEditGoalData(
        val id: Int,
        val workout: WorkoutEditGoals,
        val diet: DietData,
        val overall_calories: OverallCal,
        val steps: Int,
        val water: Int,
        val short_goals: ShortGoalsData,
        val long_goals: LongGoalsData,
    ) {
        data class WorkoutEditGoals(
            val calorie_burn: String,
            val unit: String? = null,
        )

        data class DietData(
            val total_carbs: Int,
            val consume_carbs: Int,
            val total_proteins: Int,
            val consume_protein: Int,
            val total_fats: Int,
            val consume_fat: Int
        )

        data class OverallCal(
            val overall_calories: String,
            val unit: String
        )

        data class ShortGoalsData(
            val selected_goal_id: Int,
            val answer: String,
            val custom: String,
            val target_value: Int,
            val target_date: String? = null
        )

        data class LongGoalsData(
            val selected_goal_id: Int,
            val answer: String,
            val custom: String,
            val target_value: Int,
            val target_date: String
        )
    }
}
