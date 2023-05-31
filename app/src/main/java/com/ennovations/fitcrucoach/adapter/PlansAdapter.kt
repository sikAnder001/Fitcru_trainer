package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.databinding.PlansRvItemBinding
import com.ennovations.fitcrucoach.model.PlansModel

class PlansAdapter(

    private val plansModel: ArrayList<PlansModel>,

    val listener: PlansOnClickListener,

    private val context: Context?
) : RecyclerView.Adapter<PlansAdapter.ViewHolder>() {

    inner class ViewHolder(plansBinding: PlansRvItemBinding) :
        RecyclerView.ViewHolder(plansBinding.root) {

        var plansBinding = plansBinding

        fun bind(plansModel: PlansModel, position: Int) {

            plansBinding.apply {

                Glide.with(context!!).load(plansModel.coachImg)
                    .into(coachImage)

                coachName.text = plansModel.coachName

                joinTv.text = plansModel.joinDate

                expireTv.text = plansModel.expireDate

            }

            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(position)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val plansBinding = PlansRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(plansBinding)
    }

    override fun onBindViewHolder(holder: PlansAdapter.ViewHolder, position: Int) {
        holder.bind(plansModel[position], position)
    }

    override fun getItemCount(): Int {
        return plansModel.size
    }

    interface PlansOnClickListener {

        fun onClick(position: Int)

    }
}