package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.databinding.SelectWorkoutChildRvItemBinding
import com.ennovations.fitcrucoach.model.SelectWorkoutModel

class SelectWorkoutChildAdapter(

    private val selectWorkoutChildModel: ArrayList<SelectWorkoutModel.SelectWorkoutChildModel>,

    private val context: Context?,

    private val listener: SelectWorkoutOnClickListener

) : RecyclerView.Adapter<SelectWorkoutChildAdapter.ViewHolder>() {

    inner class ViewHolder(selectWorkoutChildBinding: SelectWorkoutChildRvItemBinding) :
        RecyclerView.ViewHolder(selectWorkoutChildBinding.root) {

        var selectWorkoutChildBinding = selectWorkoutChildBinding

        fun bind(
            selectWorkoutChildModel: SelectWorkoutModel.SelectWorkoutChildModel,
            position: Int
        ) {

            selectWorkoutChildBinding.apply {

                Glide.with(context!!).load(selectWorkoutChildModel.workoutImg)
                    .into(workoutImage)

                workoutMainTitle.text = selectWorkoutChildModel.workoutMainTitle

                workoutTotalSessions.text = selectWorkoutChildModel.workoutTotalSession

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
    ): SelectWorkoutChildAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val selectWorkoutChildBinding =
            SelectWorkoutChildRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(selectWorkoutChildBinding)
    }

    override fun onBindViewHolder(holder: SelectWorkoutChildAdapter.ViewHolder, position: Int) {

        holder.bind(selectWorkoutChildModel[position], position)

    }

    override fun getItemCount(): Int {

        return selectWorkoutChildModel.size

    }

    interface SelectWorkoutOnClickListener {

        fun onClick(position: Int)

    }
}