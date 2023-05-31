package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.PlanDetailedWorkoutChildRvBinding
import com.ennovations.fitcrucoach.model.PlanDetailedWorkoutModel

class PlanDetailedWorkoutChildAdapter(

    private val planDetailedChildModel: ArrayList<PlanDetailedWorkoutModel.PlanDetailedChildItem>,

    private val context: Context?,

    private val listener: MyOnClickListenerPlanDetailed

) : RecyclerView.Adapter<PlanDetailedWorkoutChildAdapter.ViewHolder>() {

    inner class ViewHolder(planDetailedChildBinding: PlanDetailedWorkoutChildRvBinding) :
        RecyclerView.ViewHolder(planDetailedChildBinding.root) {

        var planDetailedChildBinding = planDetailedChildBinding

        fun bind(
            planDetailedChildModel: PlanDetailedWorkoutModel.PlanDetailedChildItem,
            position: Int
        ) {
            planDetailedChildBinding.apply {

                Glide.with(context!!).load(planDetailedChildModel.coachImg)
                    .into(coachImage)

                workoutType.text = planDetailedChildModel.workoutType

                durationTv.text = planDetailedChildModel.duration

                personTv.text = planDetailedChildModel.person

                pendingOrCompletedTv.text = planDetailedChildModel.pendingCompletedStatusTv

                if (pendingOrCompletedTv.text == "Pending") {

                    pendingAndCompletedContainer.setBackgroundResource(R.drawable.pending_background)

                } else if (pendingOrCompletedTv.text == "Completed") {

                    pendingAndCompletedContainer.setBackgroundResource(R.drawable.completed_background)

                } else {

                    pendingAndCompletedContainer.setBackgroundResource(R.drawable.pending_background)

                }
            }

            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(position)

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanDetailedWorkoutChildAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val planDetailedChildBinding =
            PlanDetailedWorkoutChildRvBinding.inflate(inflater, parent, false)

        return ViewHolder(planDetailedChildBinding)
    }

    override fun onBindViewHolder(
        holder: PlanDetailedWorkoutChildAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(planDetailedChildModel[position], position)

    }

    override fun getItemCount(): Int {
        return planDetailedChildModel.size
    }

    interface MyOnClickListenerPlanDetailed {

        fun onClick(position: Int)

    }
}