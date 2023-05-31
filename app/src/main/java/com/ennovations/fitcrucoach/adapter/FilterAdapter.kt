package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.databinding.FilterRvItemBinding
import com.ennovations.fitcrucoach.model.FilterModel

class FilterAdapter(

    private val context: Context,

    private val listener: FilterOnClickListener

) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var filterModel: List<FilterModel> = ArrayList()

    inner class ViewHolder(filterItemBinding: FilterRvItemBinding) :
        RecyclerView.ViewHolder(filterItemBinding.root) {

        var filterItemBinding = filterItemBinding

        fun bind(filterModel: FilterModel, position: Int) {

            filterItemBinding.apply {

                tooltipText.text = filterModel.text

            }

            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(position)

            }

        }

    }

    fun addData(list: List<FilterModel>) {

        this.filterModel = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val filterItemBinding =
            FilterRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(filterItemBinding)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bind(filterModel[position], position)
    }

    override fun getItemCount(): Int {
        return filterModel.size
    }

    interface FilterOnClickListener {

        fun onClick(position: Int)

    }
}