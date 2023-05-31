package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.HabitRvItemBinding
import com.ennovations.fitcrucoach.response.ClientDetailResponse

class PlanDetailHabitAdapter(
    private val context: Context?,
    private val listener: MyOnClickListenerHabitPlanDetailed
) : RecyclerView.Adapter<PlanDetailHabitAdapter.ViewHolder>() {

    private var list = listOf<ClientDetailResponse.Data.Habit>()

    inner class ViewHolder(habitBinding: HabitRvItemBinding) :
        RecyclerView.ViewHolder(habitBinding.root) {
        var habitBinding = habitBinding

        fun bind(
            data: ClientDetailResponse.Data.Habit,
            position: Int
        ) {
            habitBinding.apply {

                Glide.with(context!!).load(data.banner)
                    .placeholder(R.drawable.place_holder)
                    .into(coachImage)

                workoutType.text = data.name
                descriptionTv.text = data.content

                /*durationTv.text = data.time.toString()

                personTv.text = data.cals.toString()*/

                if (data.status == 1) {
                    pendingOrCompletedTv.text = "Completed"
                    pendingAndCompletedContainer.setBackgroundResource(R.drawable.completed_background)
                } else {
                    pendingOrCompletedTv.text = "Pending"
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
    ): PlanDetailHabitAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val habitBinding =
            HabitRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(habitBinding)
    }

    override fun onBindViewHolder(holder: PlanDetailHabitAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ClientDetailResponse.Data.Habit>?) {
        this.list = list!!
        notifyDataSetChanged()
    }

    interface MyOnClickListenerHabitPlanDetailed {

        fun onClick(position: Int)

    }
}