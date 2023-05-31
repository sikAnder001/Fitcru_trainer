package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.databinding.PlanDetailsWorkoutRvMainItemBinding
import com.ennovations.fitcrucoach.model.PlanDetailedWorkoutModel

class PlanDetailedWorkoutParentAdapter(
    private val mainPlanDetailedModel: ArrayList<PlanDetailedWorkoutModel>,

    private val context: Context?,

    private val listener: PlanDetailedWorkoutChildAdapter.MyOnClickListenerPlanDetailed

) : RecyclerView.Adapter<PlanDetailedWorkoutParentAdapter.ViewHolder>() {


    inner class ViewHolder(mainPlanDetailedBinding: PlanDetailsWorkoutRvMainItemBinding) :

        RecyclerView.ViewHolder(mainPlanDetailedBinding.root) {

        var mainPlanDetailedBinding = mainPlanDetailedBinding

        fun bind(
            mainPlanDetailedModel: PlanDetailedWorkoutModel,
            position: Int
        ) {

            mainPlanDetailedBinding.apply {

                titleTv.text = mainPlanDetailedModel.title

                setChildItemRV(
                    planDetailedWorkoutChildRv,
                    mainPlanDetailedModel.planDetailedWorkoutChildItems
                )
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanDetailedWorkoutParentAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val mainPlanDetailedBinding =
            PlanDetailsWorkoutRvMainItemBinding.inflate(inflater, parent, false)

        return ViewHolder(mainPlanDetailedBinding)
    }

    override fun onBindViewHolder(
        holder: PlanDetailedWorkoutParentAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(mainPlanDetailedModel[position], position)
    }

    override fun getItemCount(): Int {
        return mainPlanDetailedModel.size
    }

    private fun setChildItemRV(
        recyclerView: RecyclerView,
        planDetailedChildModel: ArrayList<PlanDetailedWorkoutModel.PlanDetailedChildItem>
    ) {

        val itemRecyclerAdapter = PlanDetailedWorkoutChildAdapter(
            planDetailedChildModel,
            context,
            object : PlanDetailedWorkoutChildAdapter.MyOnClickListenerPlanDetailed {
                override fun onClick(position: Int) {

                    listener.onClick(position)

                }
            })

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = itemRecyclerAdapter
    }

}