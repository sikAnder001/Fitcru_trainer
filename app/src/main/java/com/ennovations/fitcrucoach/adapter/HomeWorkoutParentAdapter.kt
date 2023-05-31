package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.databinding.HomeWorkoutMainRvItemBinding
import com.ennovations.fitcrucoach.model.HomeWorkoutModel

class HomeWorkoutParentAdapter(

    private val mainHomeWorkoutModel: ArrayList<HomeWorkoutModel>,

    private val context: Context?,

    private val listener: HomeWorkoutChildAdapter.MyOnClickListenerHomeWorkout


) : RecyclerView.Adapter<HomeWorkoutParentAdapter.ViewHolder>() {

    inner class ViewHolder(mainHomeWorkoutBinding: HomeWorkoutMainRvItemBinding) :
        RecyclerView.ViewHolder(mainHomeWorkoutBinding.root) {

        var mainHomeWorkoutBinding = mainHomeWorkoutBinding

        fun bind(mainHomeWorkoutModel: HomeWorkoutModel, position: Int) {

            mainHomeWorkoutBinding.apply {

                workoutTitle.text = mainHomeWorkoutModel.workoutTitle

                setChildItemRV(homeWorkoutChildRv, mainHomeWorkoutModel.homeWorkoutChildItem)

            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeWorkoutParentAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val mainHomeWorkoutBinding =
            HomeWorkoutMainRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(mainHomeWorkoutBinding)
    }

    override fun onBindViewHolder(holder: HomeWorkoutParentAdapter.ViewHolder, position: Int) {
        holder.bind(mainHomeWorkoutModel[position], position)
    }

    override fun getItemCount(): Int {
        return mainHomeWorkoutModel.size
    }

    private fun setChildItemRV(
        recyclerView: RecyclerView,
        homeWorkoutChildModel: ArrayList<HomeWorkoutModel.HomeWorkoutChildModel>
    ) {

        val itemRecyclerAdapter = HomeWorkoutChildAdapter(
            homeWorkoutChildModel,
            context,
            object : HomeWorkoutChildAdapter.MyOnClickListenerHomeWorkout {
                override fun onClick(position: Int) {

                    listener.onClick(position)
                }
            })

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = itemRecyclerAdapter
    }


}