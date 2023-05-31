package com.ennovations.fitcrucoach.response

data class MealTypeDetailResponse(
    val error_code: Int,
    val message: String,
    val data: Data
) {

    data class Data(

        val food_data: List<FoodDatum>,

        val benefits: Benefits
    ) {
        data class Benefits(
            val carbs: Int,

            val consume_carbs: Int,

            val protein: Int,

            val consume_protein: Int,

            val fat: Int,

            val consume_fat: Int
        )

        data class FoodDatum(
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

            val meal_type: MealType,

            val food: Food,

            val is_complete: String

        ) {
            data class Food(
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
                val deleted: Long,

                val weight_gram: Int,

                val img: Any? = null,

                val size_per_unit: Any? = null,

                val rich: String,

                val rich_value: String,

                val image_url: String,

                val cooking_today: String
            )

            data class MealType(
                val id: Int,
                val mealtype: String
            )

        }

    }

}