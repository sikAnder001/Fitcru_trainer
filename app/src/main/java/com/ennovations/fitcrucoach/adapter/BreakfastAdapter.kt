package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.databinding.BreakfastRvItemBinding
import com.ennovations.fitcrucoach.model.BreakfastModel

class BreakfastAdapter(

    private val breakfastModel: ArrayList<BreakfastModel>,

    private val context: Context?,

    private val listener: BreakfastOnClickListener

) : RecyclerView.Adapter<BreakfastAdapter.ViewHolder>() {

    inner class ViewHolder(breakfastItemBinding: BreakfastRvItemBinding) :
        RecyclerView.ViewHolder(breakfastItemBinding.root) {

        var breakfastItemBinding = breakfastItemBinding

        fun bind(
            breakfastModel: BreakfastModel,
            position: Int
        ) {
            breakfastItemBinding.apply {

                Glide.with(context!!).load(breakfastModel.img)
                    .into(breakfastImage)

                breakfastTitle.text = breakfastModel.title

                breakfastTime.text = breakfastModel.time
            }
            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(position)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakfastAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val breakfastItemBinding =
            BreakfastRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(breakfastItemBinding)
    }

    override fun onBindViewHolder(holder: BreakfastAdapter.ViewHolder, position: Int) {
        holder.bind(breakfastModel[position], position)
    }

    override fun getItemCount(): Int {
        return breakfastModel.size
    }

    interface BreakfastOnClickListener {

        fun onClick(position: Int)

    }
}