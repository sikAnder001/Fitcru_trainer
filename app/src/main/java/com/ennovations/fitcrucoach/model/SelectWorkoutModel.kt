package com.ennovations.fitcrucoach.model

data class SelectWorkoutModel(
    val workoutTitle: String,
    val selectWorkoutChildItem: ArrayList<SelectWorkoutChildModel>
) {
    data class SelectWorkoutChildModel(
        val workoutImg: Int,
        val workoutMainTitle: String,
        val workoutTotalSession: String
    )
}
