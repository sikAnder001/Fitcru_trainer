package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.PlanDetailedWorkoutChildRvItemsBinding
import com.ennovations.fitcrucoach.response.ClientDetailResponse

class WorkoutAdapter(val context: Context?) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    private var list = listOf<ClientDetailResponse.Data.WorkoutElement>()

    inner class ViewHolder(val binding: PlanDetailedWorkoutChildRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ClientDetailResponse.Data.WorkoutElement) {

            binding.apply {

                if (data.session_type == "rest") {
                    mainImageContainer.visibility = View.GONE
                    targetTv.visibility = View.GONE
                    equipTv.visibility = View.GONE
                    workoutType.text = "Rest"

                    pendingAndCompletedContainer.visibility = View.GONE

                } else if (data.session_type == "body_stat") {
                    equipTv.visibility = View.GONE
                    targetTv.visibility = View.GONE

                    mainImageContainer.visibility = View.GONE

                    pendingAndCompletedContainer.visibility = View.GONE

                    workoutType.text = "Update your statistics"
                } else if (data.session_type == "photo") {

                    pendingAndCompletedContainer.visibility = View.GONE

                    mainImageContainer.visibility = View.GONE

                    equipTv.visibility = View.GONE
                    targetTv.visibility = View.GONE
                    workoutType.text = "Upload photo"

                } else if (data.session_type == "cardio") {

                    pendingAndCompletedContainer.visibility = View.GONE

                    if (data.equipments != null) {
                        equipTv.visibility = View.VISIBLE
                        equipTv.text = data.equipments
                    } else {
                        equipTv.visibility = View.GONE
//                        targetDuration.visibility = View.GONE
                    }

                    if (!data.cardio_target.isNullOrEmpty()) {
                        targetTv.visibility = View.VISIBLE
                        targetTv.text = "Target- ${data.cardio_target} ${data.cardio_val}"
                    } else targetTv.visibility = View.GONE

                    mainImageContainer.visibility = View.VISIBLE
                    Glide.with(context!!)
                        .load(list!![position].img)
                        .placeholder(R.drawable.place_holder)
                        .into(coachImage)

                } else {
                    equipTv.visibility = View.GONE
                    targetTv.visibility = View.GONE

                    mainImageContainer.visibility = View.VISIBLE

                    Glide.with(context!!).load(data.banner)
                        .placeholder(R.drawable.place_holder)
                        .into(coachImage)

                    workoutType.text = data.session_name
                    pendingAndCompletedContainer.visibility = View.VISIBLE
                    if (data.session_completed == 1) {
                        pendingOrCompletedTv.text = "Completed"
                        pendingAndCompletedContainer.setBackgroundResource(R.drawable.completed_background)
                    } else {
                        pendingOrCompletedTv.text = "Pending"
                        pendingAndCompletedContainer.setBackgroundResource(R.drawable.pending_background)
                    }
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PlanDetailedWorkoutChildRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (e: Exception) {
            0
        }
    }

    fun setList(list: List<ClientDetailResponse.Data.WorkoutElement>?) {
        if (list != null) {
            this.list = list
        }
        notifyDataSetChanged()
    }

}
