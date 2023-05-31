package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.MealDetailAdapter
import com.ennovations.fitcrucoach.databinding.FragmentMealDetailsBinding
import com.ennovations.fitcrucoach.model.MealDetailModel

class MealDetailsFragment : Fragment() {

    private lateinit var mealDetailsBinding: FragmentMealDetailsBinding

    private lateinit var mainDetailsAdapter: MealDetailAdapter

    private lateinit var mainDetailsModel: ArrayList<MealDetailModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mealDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_meal_details, container, false)

//        getBreakfast()

        return mealDetailsBinding.root
    }

    /* private fun getBreakfast() {
         val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

         mealDetailsBinding.rvMealDetail.layoutManager = linearLayout

         mealDetailsBinding.rvMealDetail.setHasFixedSize(true)

         mainDetailsModel = setDataInMealList()

         mainDetailsAdapter = MealDetailAdapter(mainDetailsModel, context, this)

         mealDetailsBinding.rvMealDetail.adapter = mainDetailsAdapter
     }*/

    private fun setDataInMealList(): ArrayList<MealDetailModel> {
        val meal: ArrayList<MealDetailModel> = ArrayList()
        meal.apply {
            add(
                MealDetailModel(
                    "Meal",
                    "description"
                )
            )

            add(
                MealDetailModel(
                    "Meal",
                    "description"
                )
            )

            add(
                MealDetailModel(
                    "Meal",
                    "description"
                )
            )

            add(
                MealDetailModel(
                    "Meal",
                    "description"
                )
            )

            add(
                MealDetailModel(
                    "Meal",
                    "description"
                )
            )

            add(
                MealDetailModel(
                    "Meal",
                    "description"
                )
            )

            return meal
        }

    }


}