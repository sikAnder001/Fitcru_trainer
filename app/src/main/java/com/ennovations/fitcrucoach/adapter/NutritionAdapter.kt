package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.NutritionListBinding
import com.ennovations.fitcrucoach.response.ClientDetailResponse
import java.text.SimpleDateFormat
import java.util.*

class NutritionAdapter(
    val context: Context?, private val listener: NutritionOnClickListener
) :
    RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {

    private var list = listOf<ClientDetailResponse.Data.TodaysTask>()

    inner class ViewHolder(val binding: NutritionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ClientDetailResponse.Data.TodaysTask, position: Int) {

            binding.apply {

                Glide.with(context!!).load(data.meal_type_name.foods.image_url)
                    .placeholder(R.drawable.place_holder)
                    .into(coachImage)

                tvMealName.text = data.meal_name

                var time = getTime(data.time)
                tvTime.text = time

                tvMealType.text = data.meal_type_name.mealtype

                mainContainer.setOnClickListener {
                    listener.onClick(data.meal_type_id, data.date)
                }
                if (data.is_complete.equals("1")) {
                    pendingOrCompletedTv.text = "Completed"
                    pendingAndCompletedContainer.setBackgroundResource(R.drawable.completed_background)
                } else {
                    pendingOrCompletedTv.text = "Pending"
                    pendingAndCompletedContainer.setBackgroundResource(R.drawable.pending_background)
                }
            }

            itemView.setOnClickListener {

                val position = adapterPosition
                listener.onClick(position, data.date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionAdapter.ViewHolder {
        return ViewHolder(
            NutritionListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(list[position], position)

    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (e: Exception) {
            0
        }
    }

    fun setList(list: List<ClientDetailResponse.Data.TodaysTask>?) {
        this.list = list!!
        notifyDataSetChanged()
    }

    interface NutritionOnClickListener {
        fun onClick(mealId: Int, date: String)
    }


    private fun getTime(time: String): String {
        val sdf = SimpleDateFormat("HH:mm")
        var date: Date? = null
        date = sdf.parse(time)
        val c: Calendar = Calendar.getInstance()
        c.time = date
        val hoour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        var hour = hoour
        var am_pm = ""
        when {
            hour == 0 -> {
                hour += 12
                am_pm = "AM"
            }
            hour == 12 -> am_pm = "PM"
            hour > 12 -> {
                hour -= 12
                am_pm = "PM"
            }
            else -> am_pm = "AM"
        }
        val hours = if (hour < 10) "0$hour" else hour
        val min = if (minute < 10) "0$minute" else minute

        return "$hours:$min $am_pm"
    }
}
