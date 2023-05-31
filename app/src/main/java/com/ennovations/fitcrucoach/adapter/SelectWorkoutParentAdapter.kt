package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.databinding.SelectWorkoutMainRvItemBinding
import com.ennovations.fitcrucoach.model.SelectWorkoutModel

class SelectWorkoutParentAdapter(

    private val mainSelectWorkoutModel: ArrayList<SelectWorkoutModel>,

    private val context: Context?,

    val listener: SelectWorkoutChildAdapter.SelectWorkoutOnClickListener

) : RecyclerView.Adapter<SelectWorkoutParentAdapter.ViewHolder>() {

    inner class ViewHolder(mainSelectWorkoutBinding: SelectWorkoutMainRvItemBinding) :
        RecyclerView.ViewHolder(mainSelectWorkoutBinding.root) {

        var mainSelectWorkoutBinding = mainSelectWorkoutBinding

        fun bind(
            mainSelectWorkoutModel: SelectWorkoutModel,
            position: Int
        ) {

            mainSelectWorkoutBinding.apply {

                workoutTitle.text = mainSelectWorkoutModel.workoutTitle

                setChildItemRV(
                    selectPlanChildRv, mainSelectWorkoutModel.selectWorkoutChildItem
                )
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectWorkoutParentAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val mainSelectWorkoutBinding =
            SelectWorkoutMainRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(mainSelectWorkoutBinding)
    }

    override fun onBindViewHolder(holder: SelectWorkoutParentAdapter.ViewHolder, position: Int) {
        holder.bind(mainSelectWorkoutModel[position], position)
    }

    override fun getItemCount(): Int {
        return mainSelectWorkoutModel.size
    }

    private fun setChildItemRV(
        recyclerView: RecyclerView,
        selectWorkoutChildModel: ArrayList<SelectWorkoutModel.SelectWorkoutChildModel>
    ) {
        val itemRecyclerAdapter = SelectWorkoutChildAdapter(
            selectWorkoutChildModel,
            context,
            object : SelectWorkoutChildAdapter.SelectWorkoutOnClickListener {
                override fun onClick(position: Int) {

                    listener.onClick(position)

                }
            })

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = itemRecyclerAdapter
    }

}