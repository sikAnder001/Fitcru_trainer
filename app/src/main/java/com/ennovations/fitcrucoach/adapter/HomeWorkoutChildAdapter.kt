package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.databinding.HomeWorkoutChildRvItemBinding
import com.ennovations.fitcrucoach.model.HomeWorkoutModel

class HomeWorkoutChildAdapter(

    private val homeWorkoutChildModel: ArrayList<HomeWorkoutModel.HomeWorkoutChildModel>,

    private val context: Context?,

    private val listener: MyOnClickListenerHomeWorkout


) : RecyclerView.Adapter<HomeWorkoutChildAdapter.ViewHolder>() {

    inner class ViewHolder(homeWorkoutChildBinding: HomeWorkoutChildRvItemBinding) :
        RecyclerView.ViewHolder(homeWorkoutChildBinding.root) {

        var homeWorkoutChildBinding = homeWorkoutChildBinding

        fun bind(homeWorkoutChildModel: HomeWorkoutModel.HomeWorkoutChildModel, position: Int) {

            homeWorkoutChildBinding.apply {

                Glide.with(context!!).load(homeWorkoutChildModel.workoutThumbnailImg)
                    .into(workoutThumbnailImage)

                workoutSubTitle.text = homeWorkoutChildModel.workoutSubTitle

                totalDuration.text = homeWorkoutChildModel.duration

                person.text = homeWorkoutChildModel.person

                workoutCategory.text = homeWorkoutChildModel.workoutCategory

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
    ): HomeWorkoutChildAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val homeWorkoutChildBinding =
            HomeWorkoutChildRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(homeWorkoutChildBinding)
    }

    override fun onBindViewHolder(holder: HomeWorkoutChildAdapter.ViewHolder, position: Int) {
        holder.bind(homeWorkoutChildModel[position], position)
    }

    override fun getItemCount(): Int {
        return homeWorkoutChildModel.size
    }

    interface MyOnClickListenerHomeWorkout {

        fun onClick(position: Int)

    }
}