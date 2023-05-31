package com.ennovations.fitcrucoach.response

data class ClientDetailResponse(
    val error_code: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val profile: Profile,

        val progress: Progress,

        val todays_task: List<TodaysTask>,

        val workout: List<WorkoutElement>,

        val habit: List<Habit>,
    ) {

        data class Profile(
            val id: Int,
            val name: String,
            val image: String? = null,

            val image_url: String? = null,

            val goal: String,

            val short_goal: String,

            val target_value: Int,

            val target_date: String
        )

        data class Habit(
            val id: Int,

            val habit_category_id: Int,

            val name: String,
            val content: String,
            val video: String,

            val video_url: Any? = null,

            val banner: String,
            val status: Int,
            val time: Any? = null,
            val cals: Any? = null,
            val equipment: Any? = null,

            )

        data class Progress(
            val workout: ProgressWorkout,
            val diet: Diet,
            val water: Water,
            val steps: Steps
        ) {
            data class Diet(
                val diet_val: String,
                val kcal_burnt: String,
                val unit: String
            )

            data class Steps(
                val step_count_val: String,
                val step_target: String
            )

            data class Water(
                val water_taget: String,
                val water_used_val: String,
                val unit: String
            )

            data class ProgressWorkout(
                val workout_kcal_target: String,
                val workout_kcal_burnt: String,
                val unit: String
            )
        }

        data class TodaysTask(
            val id: Int,

            val user_id: Int,

            val food_id: Int,

            val serving_size_id: Int,

            val time: String,

            val meal_name: String,

            val meal_type_id: Int,

            val quantity: String,
            val unit: String,

            val total_calorie: String,

            val date: String,

            val meal_type_name: MealTypeName,

            val is_complete: String
        ) {
            data class MealTypeName(
                val id: Int,
                val mealtype: String,
                val foods: Foods
            ) {
                data class Foods(
                    val id: Int,

                    val foodtype_id: Int,

                    val diet_preference_id: Int,

                    val name: String,
                    val image: Any? = null,
                    val alergies: Any? = null,

                    val health_issues: Any? = null,

                    val calories: String,
                    val proteins: String,
                    val fat: String,
                    val carbs: String,
                    val sulphur: String,
                    val phosp: String,
                    val potassium: String,
                    val fiber: String,
                    val vitamina: String,
                    val iron: String,
                    val vitaminb: String,
                    val reflink: String,
                    val tags: String,
                    val unit: String,
                    val deleted: Int,

                    val weight_gram: Int,

                    val img: Any? = null,

                    val size_per_unit: Any? = null,

                    val rich: String,

                    val rich_value: String,


                    val image_url: String,

                    val cooking_today: String,


                    val servin_size: List<ServinSize>? = null
                ) {
                    data class ServinSize(
                        val id: Int,

                        val food_id: Int,

                        val serving_size: String,

                        val unit: String,
                        val calorie: String
                    )
                }
            }
        }

        data class WorkoutElement(
            val id: Int,
            val banner: String,

            val session_name: String,

            val description: String,

            val duration: String,

            val cals: Any? = null,
            val img: String? = null,
            val video: String? = null,
            val status: Int,

            val coach_name: String? = null,
            val session_type: String? = null,
            val cardio_target: String? = null,
            val cardio_val: String? = null,

            val benefits: String,
            val equipments: String?,
            val focus: String,
            val intensity: String,
            val level: String,
            val musclegroup: String,
            val service: String,
            val studio: String,
            val tags: String,
            val types: String,
            val video_url: String? = null,
            val workouts: String,
            val session_completed: Int? = null,

            )
    }
}