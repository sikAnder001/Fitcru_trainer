package com.ennovations.fitcrucoach.model

data class HomeWorkoutModel(
    val workoutTitle: String,
    val homeWorkoutChildItem: ArrayList<HomeWorkoutChildModel>
) {
    data class HomeWorkoutChildModel(
        val workoutThumbnailImg: Int,
        val workoutSubTitle: String,
        val duration: String,
        val person: String,
        val workoutCategory: String
    )
}
