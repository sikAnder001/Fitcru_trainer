package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.databinding.ClientListBinding
import com.ennovations.fitcrucoach.model.MyClientPjo

class MyClientAdapter(val context: Context?, private val listener: ClientOnClickListener) :
    RecyclerView.Adapter<MyClientAdapter.ViewHolder>() {

    private var list = ArrayList<MyClientPjo>()

    inner class ViewHolder(val binding: ClientListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyClientPjo, position: Int) {
            binding.apply {
            }

            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(position)

            }
        }
    }

    fun setList(list: ArrayList<MyClientPjo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ClientListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(list[position], position)

    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (e: Exception) {
            0
        }
    }

    interface ClientOnClickListener {

        fun onClick(position: Int)

    }

}
