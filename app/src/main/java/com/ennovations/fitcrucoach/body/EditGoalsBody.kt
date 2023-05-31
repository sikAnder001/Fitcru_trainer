package com.ennovations.fitcrucoach.body

data class EditGoalsBody(
    val user_id: Int,
    val calorie_burn: String,
    val consume_carbs: Int,
    val consume_protein: Int,
    val consume_fat: Int,
    val overall_calories: String,
    val steps: Int,
    val water: Int,
    val short_goal: String,
    val short_goal_custom: String,
    val short_goal_value: Int? = null,
    val short_goal_date: String,
    val long_goal: String,
    val long_goal_custom: String,
    val long_goal_value: Int? = null,
    val long_goal_date: String,

    )
