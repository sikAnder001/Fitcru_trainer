package com.ennovations.fitcrucoach.model

data class PlanDetailedWorkoutModel(
    val title: String,
    val planDetailedWorkoutChildItems: ArrayList<PlanDetailedChildItem>
) {
    data class PlanDetailedChildItem(
        val coachImg: Int,
        val pendingCompletedStatusTv: String,
        val workoutType: String,
        val duration: String,
        val person: String
    )
}
