package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.databinding.MealDetailItemBinding
import com.ennovations.fitcrucoach.response.MealTypeDetailResponse

class MealDetailAdapter(

    private val mealModel: List<MealTypeDetailResponse.Data.FoodDatum>,

    private val context: Context

) : RecyclerView.Adapter<MealDetailAdapter.ViewHolder>() {

    inner class ViewHolder(mealDetailItemBinding: MealDetailItemBinding) :
        RecyclerView.ViewHolder(mealDetailItemBinding.root) {

        var mealDetailItemBinding = mealDetailItemBinding
        fun bind(
            mealData: MealTypeDetailResponse.Data.FoodDatum,
            position: Int
        ) {
            mealDetailItemBinding.apply {

                tvMealType.text = mealData.meal_name
                tvDescription.text = "${mealData.quantity} ${mealData.unit}"
                if (mealData.is_complete.equals("1")) {
                    checkbox.isChecked = true
                }

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealDetailAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val mealDetailItemBinding = MealDetailItemBinding.inflate(inflater, parent, false)

        return ViewHolder(mealDetailItemBinding)
    }

    override fun onBindViewHolder(holder: MealDetailAdapter.ViewHolder, position: Int) {
        holder.bind(mealModel[position], position)
    }

    override fun getItemCount(): Int {
        return mealModel.size
    }


}